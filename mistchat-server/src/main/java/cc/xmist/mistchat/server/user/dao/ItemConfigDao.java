package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.entity.ItemConfig;
import cc.xmist.mistchat.server.user.mapper.ItemConfigMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 功能物品配置表 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-16
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig> {

    /**
     * 获取所有的物品信息
     *
     * @return
     */
    public List<ItemConfig> getAllBadgeList() {
        return lambdaQuery().ne(ItemConfig::getId,0).list();
    }

    /**
     * 根据id列表获取徽章信息
     *
     * @param badgeIdList
     * @return
     */
    public List<ItemConfig> getBadges(List<Long> badgeIdList) {
        return lambdaQuery()
                .in(ItemConfig::getId,badgeIdList)
                .list();
    }
}
