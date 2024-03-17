package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.constant.StatusType;
import cc.xmist.mistchat.server.common.enums.IdempotentType;
import cc.xmist.mistchat.server.common.enums.Item;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.user.UserAdapter;
import cc.xmist.mistchat.server.user.dao.UserBackpackDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.entity.UserBackpack;
import cc.xmist.mistchat.server.user.model.resp.BadgeVo;
import cc.xmist.mistchat.server.user.model.resp.WearingBadgeVo;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemService {
    private final RedissonClient redissonClient;
    private final UserBackpackDao userBackpackDao;
    private final UserDao userDao;


    /**
     * 获取徽章列表
     *
     * @param uid
     * @return
     */
    public List<BadgeVo> getBadgeList(Long uid) {
        // 徽章详细信息
        List<Item> allItems = Item.all;
        // 用户徽章列表
        List<UserBackpack> userBadges = userBackpackDao.getBadges(uid);

        // 用户已经佩戴了的徽章id
        User user = userDao.getUser(uid);
        Long usedItemId = user.getItemId();

        return UserAdapter.buildBadgeResponse(uid, usedItemId, allItems, userBadges);
    }

    /**
     * 穿戴徽章
     *
     * @param uid
     * @param badgeId
     */
    public void wearBadge(Long uid, Long badgeId) {
        List<UserBackpack> ownBadges = userBackpackDao.getBadges(uid);

        List<Long> ownBadgeIds = ownBadges.stream()
                .map(badge -> badge.getItemId())
                .collect(Collectors.toList());

        if (!ownBadgeIds.contains(badgeId)) {
            throw new ParamException("还未拥有这个勋章");
        }

        userDao.wearBadge(uid, badgeId);
    }

    /**
     * 发放物品
     * 幂等号 = itemId + idempotentType + businessId
     *
     * @param uid            用户id
     * @param itemType       物品类型
     * @param idempotentType 幂等类型/发放场景
     * @param businessId     业务id
     */
    public void acquireItem(Long uid, Item item, IdempotentType idempotentType, String businessId) {
        String idempotent = String.format("%d_%d_%s", item.getCode(), idempotentType.getCode(), businessId);
        RLock lock = redissonClient.getLock("acquire" + idempotent);
        lock.tryLock();

        try {
            // 已经发放的
            UserBackpack backpack = userBackpackDao.getItem(idempotent);
            if (item != null) {
                return;
            }

            // 其它业务检查
            // ...

            UserBackpack newItem = UserBackpack.builder()
                    .itemId(backpack.getItemId())
                    .status(StatusType.NO.key)
                    .idempotent(idempotent)
                    .uid(uid)
                    .build();

            userBackpackDao.save(newItem);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 批量获取用户穿戴的徽章
     *
     * @param uidList
     * @return
     */
    public List<WearingBadgeVo> getWaringBadgeInfo(List<Long> uidList) {
        List<WearingBadgeVo> list = userDao.getUserBatch(uidList).stream()
                .map(u -> {
                    if (u.getItemId() == null) {
                        return WearingBadgeVo.builder().uid(u.getId()).build();
                    }

                    Item item = Item.getById(u.getItemId());
                    return WearingBadgeVo.builder()
                            .uid(u.getId())
                            .itemId(item.getId())
                            .img(item.getImg())
                            .description(item.getDescription())
                            .build();
                }).collect(Collectors.toList());
        return list;
    }
}
