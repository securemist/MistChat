package cc.xmist.mistchat.server.common.cache;

import cc.xmist.mistchat.server.user.dao.ItemConfigDao;
import cc.xmist.mistchat.server.user.model.entity.ItemConfig;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemCache {
    @Resource
    private ItemConfigDao configDao;
    private List<ItemConfig> allBadges;

    public ItemConfig getById(Long id) {
        return getAllBadges().stream()
                .filter(itemConfig -> itemConfig.getId().equals(id))
                .findFirst()
                .get();
    }

    public List<ItemConfig> getAllBadges() {
        if (allBadges == null) {
            allBadges = configDao.getAllBadges();
        }
        return allBadges;
    }
}
