package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.model.entity.Black;
import cc.xmist.mistchat.server.common.enums.BlackType;
import cc.xmist.mistchat.server.user.model.mapper.BlackMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-25
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> {

    public Black get(Long uid, BlackType blackType, String target) {
        return lambdaQuery()
                .eq(Black::getUid, uid)
                .eq(Black::getType, blackType)
                .eq(Black::getTarget, target)
                .one();
    }

    private boolean exist(Long uid, BlackType blackType, String target) {
        return get(uid, blackType, target) != null;
    }

    public void addBlack(Long uid, BlackType blackType, String target) {
        // 已经存在就不添加
        if (exist(uid, blackType, target)) return;

        Black black = Black.builder()
                .type(blackType)
                .uid(uid)
                .target(target)
                .build();
        save(black);
    }

    @Cacheable(key = ":#blackType")
    public List<Black> getBlacks(BlackType blackType) {
        return lambdaQuery()
                .eq(Black::getType,blackType)
                .list();

    }
}
