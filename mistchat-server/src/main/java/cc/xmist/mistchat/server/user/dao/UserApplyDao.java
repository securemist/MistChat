package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import cc.xmist.mistchat.server.user.model.mapper.UserApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
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
    @Resource
    private UserApplyMapper userApplyMapper;

    public List<UserApply> getReceivedApplyList(Long uid) {
        return lambdaQuery()
                .eq(UserApply::getTargetId, uid)
                .list();
    }

    public List<UserApply> getForwardApplyList(Long uid) {
        return  lambdaQuery()
                .eq(UserApply::getUid,uid)
                .list();
    }
    public void addFriendApply(Long uid, Long targetId, String msg) {
        UserApply userApply = UserApply.builder()
                .applyMsg(msg)
                .uid(uid)
                .type(ApplyType.FRIEND)
                .targetId(targetId)
                .status(ApplyStatus.WAIT)
                .build();
        save(userApply);
    }

    /**
     * 处理申请
     *
     * @param uid
     * @param apply
     * @param pass  是否通过
     * @param msg
     */
    public void handleApply( Long applyId, Boolean pass, String msg) {
        ApplyStatus status = ApplyStatus.READ;

        if (pass) {
            status = ApplyStatus.PASS;
        }

        lambdaUpdate().eq(UserApply::getId, applyId)
                .set(UserApply::getStatus, status)
                .set(UserApply::getReadTime, LocalDateTime.now())
                .set(UserApply::getReplyMsg,msg)
                .update();
    }

    public boolean exist(Long uid, ApplyType type, Long targetId) {
        return find(uid, type, targetId) != null;
    }

    public UserApply find(Long uid, ApplyType type, Long targetId) {
        return lambdaQuery()
                .eq(UserApply::getUid, uid)
                .eq(UserApply::getType, type)
                .eq(UserApply::getTargetId, targetId)
                .one();
    }

    public boolean isFriend(Long uid1, Long uid2) {
        return userApplyMapper.isFriend(uid1, uid2);
    }
}
