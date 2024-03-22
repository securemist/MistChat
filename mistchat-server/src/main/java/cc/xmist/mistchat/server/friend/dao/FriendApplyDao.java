package cc.xmist.mistchat.server.friend.dao;

import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import cc.xmist.mistchat.server.common.enums.ApplyType;
import cc.xmist.mistchat.server.common.exception.IllegalParamException;
import cc.xmist.mistchat.server.friend.entity.FriendApply;
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


    public FriendApply addFriendApply(Integer uid, Integer targetUid, String msg) {
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
     * @return
     */
    public FriendApply handle(FriendApply apply, Boolean pass, String msg) {
        ApplyStatus status = ApplyStatus.READ;

        if (pass) status = ApplyStatus.PASS;

        apply.setStatus(status);
        apply.setApplyMsg(msg);
        apply.setReadTime(LocalDateTime.now());
        updateById(apply);
        return apply;
    }

    public boolean exist(Integer uid, ApplyType type, Integer targetId) {
        return get(uid, type, targetId) != null;
    }

    public FriendApply get(Integer uid, ApplyType type, Integer targetUId) {
        return lambdaQuery()
                .eq(FriendApply::getUid, uid)
                .eq(FriendApply::getTargetUid, targetUId)
                .one();
    }


    public List<FriendApply> list(Integer uid) {
        return lambdaQuery()
                .eq(FriendApply::getUid, uid)
                .or(wrapper -> wrapper.eq(FriendApply::getTargetUid, uid))
                .list();
    }

    public void read(Integer uid, Integer applyId) {
        boolean ok = lambdaUpdate()
                .set(FriendApply::getReadTime, LocalDateTime.now())
                .eq(FriendApply::getTargetUid, uid)
                .eq(FriendApply::getId, applyId)
                .update();
        if (!ok) throw new IllegalParamException();
    }
}
