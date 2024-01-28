package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatusType;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import cc.xmist.mistchat.server.user.model.mapper.UserApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-27
 */
@Service
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

    public List<UserApply> getReceivedApplyList(Long uid) {
        return lambdaQuery()
                .eq(UserApply::getTargetId,uid)
                .list();
    }

    public void addFriendApply(Long uid, Long targetId, String msg) {
        UserApply userApply = UserApply.builder()
                .msg(msg)
                .uid(uid)
                .type(ApplyType.FRIEND)
                .targetId(targetId)
                .status(ApplyStatusType.WAIT)
                .build();
        save(userApply);
    }

    public void handleApply(Long uid, UserApply apply, Boolean pass, String msg) {
        apply.setStatus(ApplyStatusType.READ);

        if (pass) {
            apply.setStatus(ApplyStatusType.PASS);
        }
        apply.setReadTime(LocalDateTime.now());
        updateById(apply);
    }

    public boolean existApply(Long uid, ApplyType type, Long targetId) {
        return lambdaQuery()
                .eq(UserApply::getUid, uid)
                .eq(UserApply::getType, type)
                .eq(UserApply::getTargetId, targetId)
                .exists();
    }
}
