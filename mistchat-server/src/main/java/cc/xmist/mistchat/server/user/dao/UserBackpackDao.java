package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.common.constant.StatusType;
import cc.xmist.mistchat.server.user.entity.UserBackpack;
import cc.xmist.mistchat.server.user.mapper.UserBackpackMapper;
import cc.xmist.mistchat.server.user.model.enums.ItemType;
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
    public Long getItemCount(Long uid, ItemType itemType) {
        Long count = lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemType.getId())
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
                .eq(UserBackpack::getStatus, StatusType.NO.status)
                .set(UserBackpack::getStatus, StatusType.YES.status)
                .update();
    }

    /**
     * 获取用户最新的物品
     *
     * @param uid
     * @param itemType
     * @return
     */
    public UserBackpack getLastItem(Long uid, ItemType itemType) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemType.getId())
                .eq(UserBackpack::getStatus, StatusType.NO.status)
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
                .in(UserBackpack::getItemId, ItemType.getBadgesId())
                .list();
    }
}
