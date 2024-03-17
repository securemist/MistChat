package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.common.constant.StatusType;
import cc.xmist.mistchat.server.common.enums.Item;
import cc.xmist.mistchat.server.user.model.entity.UserBackpack;
import cc.xmist.mistchat.server.user.model.mapper.UserBackpackMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-16
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    /**
     * 获取用户的物品数量
     *
     * @param uid      用户id
     * @param itemType 物品枚举
     * @return
     */
    public Long getItemCount(Long uid, Item item) {
        Long count = lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, item.getCode())
                .eq(UserBackpack::getStatus, StatusType.NO).count();
        return count;
    }

    /**
     * 消耗物品
     *
     * @param uid
     * @return
     */
    public boolean useItem(Long id) {
        return lambdaUpdate()
                .eq(UserBackpack::getId, id)
                .eq(UserBackpack::getStatus, StatusType.NO.key)
                .set(UserBackpack::getStatus, StatusType.YES.key)
                .update();
    }

    /**
     * 获取用户最新的物品
     *
     * @param uid
     * @param itemType
     * @return
     */
    public UserBackpack getLastItem(Long uid, Item item) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, item)
                .eq(UserBackpack::getStatus, StatusType.NO.key)
                .orderByAsc(UserBackpack::getCreateTime)
                .last("limit 1")
                .one();
    }


    /**
     * 获取所有id列表中的物品
     *
     * @param uid        用户id
     * @param itemIdList 物品id列表
     * @return
     */
    public List<UserBackpack> getItemList(Long uid, List<Long> itemIdList) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .in(UserBackpack::getItemId, itemIdList)
                .list();
    }

    /**
     * 获取用户所有的徽章列表
     * @param uid
     * @return
     */
    public List<UserBackpack> getBadges(Long uid) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .in(UserBackpack::getItemId, Item.getBadgesId())
                .list();
    }

    /**
     * 根据幂等号获取物品
     *
     * @param idempotent
     * @return
     */
    public UserBackpack getItem(String idempotent) {
        return lambdaQuery()
                .eq(UserBackpack::getIdempotent,idempotent)
                .one();
    }
}
