package cc.xmist.mistchat.server.friend.dao;

import cc.xmist.mistchat.server.friend.entity.FriendApply;
import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import cc.xmist.mistchat.server.common.enums.ApplyType;
import cc.xmist.mistchat.server.friend.mapper.FriendApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-03-11
 */
@Service
public class FriendApplyDao extends ServiceImpl<FriendApplyMapper, FriendApply> {
    private FriendApplyMapper friendApplyMapper;

    @Autowired
    public FriendApplyDao(FriendApplyMapper friendApplyMapper) {
        this.friendApplyMapper = friendApplyMapper;
    }

    public List<FriendApply> getReceivedApplyList(Long uid) {
        return lambdaQuery()
                .eq(FriendApply::getTargetUid, uid)
                .list();
    }

    public List<FriendApply> getForwardApplyList(Long uid) {
        return lambdaQuery()
                .eq(FriendApply::getUid, uid)
                .list();
    }

    public FriendApply addFriendApply(Long uid, Long targetUid, String msg) {
        FriendApply apply = FriendApply.builder()
                .applyMsg(msg)
                .uid(uid)
                .targetUid(targetUid)
                .status(ApplyStatus.WAIT)
                .build();
        save(apply);
        return apply;
    }

    /**
     * 处理申请
     *
     * @param uid
     * @param apply
     * @param pass  是否通过
     * @param msg
     */
    public void handle(Long applyId, Boolean pass, String msg) {
        ApplyStatus status = ApplyStatus.READ;

        if (pass) {
            status = ApplyStatus.PASS;
        }

        lambdaUpdate().eq(FriendApply::getId, applyId)
                .set(FriendApply::getStatus, status)
                .set(FriendApply::getReadTime, LocalDateTime.now())
                .set(FriendApply::getReplyMsg, msg)
                .update();
    }

    public boolean exist(Long uid, ApplyType type, Long targetId) {
        return find(uid, type, targetId) != null;
    }

    public FriendApply find(Long uid, ApplyType type, Long targetUId) {
        return lambdaQuery()
                .eq(FriendApply::getUid, uid)
                .eq(FriendApply::getTargetUid, targetUId)
                .one();
    }
}
