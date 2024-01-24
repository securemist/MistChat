package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.user.UserAdapter;
import cc.xmist.mistchat.server.user.entity.ItemConfig;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.entity.UserBackpack;
import cc.xmist.mistchat.server.user.model.enums.ItemType;
import cc.xmist.mistchat.server.user.model.resp.BadgeVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BadgeService extends UserServiceSupport {

    /**
     * 获取徽章列表
     *
     * @param uid
     * @return
     */
    public List<BadgeVo> getBadgeList(Long uid) {
        // 徽章详细信息
        List<ItemConfig> allBadges = itemConfigDao.getAllBadges();
        // 用户徽章列表
        List<UserBackpack> userBadges = userBackpackDao.getBadges(uid);

        // 用户已经佩戴了的徽章id
        User user = userDao.getUser(uid);
        Long usedItemId = user.getItemId();

        return UserAdapter.buildBadgeResponse(uid, usedItemId, allBadges, userBadges);
    }

    /**
     * 穿戴徽章
     *
     * @param uid
     * @param badgeId
     */
    public void wearBadge(Long uid, Long badgeId) {
        List<UserBackpack> ownBadges = userBackpackDao.getBadges(uid);
        if(!ItemType.getBadgesId().contains(badgeId)) {
            throw new ParamException("徽章不存在");
        }

        List<Long> ownBadgeIds = ownBadges.stream()
                .map(badge -> badge.getId())
                .collect(Collectors.toList());

        if(!ownBadgeIds.contains(badgeId)) {
            throw new ParamException("还未拥有这个勋章");
        }

        userDao.wearBadge(uid, badgeId);
    }
}
