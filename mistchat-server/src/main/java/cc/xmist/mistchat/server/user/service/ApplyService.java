package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.chat.model.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.model.dao.RoomFriendDao;
import cc.xmist.mistchat.server.chat.service.RoomService;
import cc.xmist.mistchat.server.common.event.UserApplyEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.user.dao.UserApplyDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.dao.UserFriendDao;
import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.entity.UserFriend;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import cc.xmist.mistchat.server.user.model.req.ApplyAddReq;
import cc.xmist.mistchat.server.user.model.req.ApplyHandleReq;
import cc.xmist.mistchat.server.user.model.resp.ApplyResp;
import cc.xmist.mistchat.server.user.model.resp.ForwardApplyVo;
import cc.xmist.mistchat.server.user.model.resp.ReceivedApplyVo;
import jakarta.annotation.Resource;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplyService {
    @Resource
    private UserApplyDao userApplyDao;
    @Resource
    private RoomService roomService;
    @Resource
    private UserFriendDao userFriendDao;
    @Resource
    private UserDao userDao;
    @Resource
    private RoomFriendDao roomFriendDao;
    @Resource
    private UserService userService;
    @Resource
    private FriendContactDao friendContactDao;
    @Resource
    private ApplicationEventPublisher eventPublisher;


    public ApplyResp friendApply(Long uid, ApplyAddReq req) {
        Long targetUid = req.getTargetId();
        if (uid == targetUid) throw new ParamException();
        if (userApplyDao.isFriend(uid, targetUid)) throw new BusinessException("你们已经是好友了，请勿重复添加");
        if (userApplyDao.exist(uid, ApplyType.FRIEND, targetUid)) throw new BusinessException("请勿重复申请");

        UserApply apply = userApplyDao.find(targetUid, ApplyType.FRIEND, uid);
        if (apply != null) {
            userApplyDao.handle(apply.getId(), true, null);
            friendApplyPass(uid, targetUid);
            return ApplyResp.build(apply);
        }

        apply = userApplyDao.addFriendApply(uid, targetUid, req.getMsg());
        return ApplyResp.build(apply);
    }


    public ApplyResp groupApply(Long uid, ApplyAddReq req) {
        UserApply apply = null;
        return ApplyResp.build(apply);
    }


    /**
     * uid 同意了 targetUid 的申请
     *
     * @param uid
     * @param targetUid
     * @return
     */
    private Long friendApplyPass(Long uid, Long targetUid) {
        UserFriend friend = userFriendDao.create(uid, targetUid);
        roomFriendDao.create(friend.getId());
        friendContactDao.firstCreate(friend.getId(), uid, targetUid);
        eventPublisher.publishEvent(new UserApplyEvent(uid, targetUid));
        return friend.getId();
    }


    public void handleGroupApply(Long uid, ApplyHandleReq req) {

    }

    public void handleFriendApply(Long uid, ApplyHandleReq req) {
        UserApply apply = userApplyDao.getById(req.getApplyId());
        if (apply == null || apply.getTargetId().equals(uid)) throw new ParamException();
        if (apply.getStatus().equals(ApplyStatus.PASS) || apply.getStatus().equals(ApplyStatus.FORBID))
            throw new BusinessException("请勿重复处理");

        userApplyDao.handle(apply.getId(), req.getPass(), req.getMsg());
        friendApplyPass(uid, apply.getTargetId());
    }


    /**
     * 获取好友申请列表
     *
     * @param uid
     * @return
     */
    public List<ReceivedApplyVo> getReceivedApplyList(Long uid) {
        // 所有申请
        List<UserApply> applyList = userApplyDao.getReceivedApplyList(uid);
        List<UserApply> forwardApplyList = userApplyDao.getForwardApplyList(uid);

        List<ReceivedApplyVo> applyVoList = applyList.stream()
                .map(apply -> {
                    return ReceivedApplyVo.builder()
                            .applyId(apply.getId())
                            .status(apply.getStatus())
                            .msg(apply.getApplyMsg())
                            .applyUserId(apply.getUid())
                            .build();
                })
                .collect(Collectors.toList());

        return applyVoList;
    }

    public List<ForwardApplyVo> getForwardApplyList(Long uid) {
        List<UserApply> applyList = userApplyDao.getForwardApplyList(uid);

        List<ForwardApplyVo> applyVoList = applyList.stream()
                .map(apply -> {
                    return ForwardApplyVo.builder()
                            .applyId(apply.getId())
                            .status(apply.getStatus())
                            .msg(apply.getApplyMsg())
                            .targetUserId(apply.getTargetId())
                            .build();
                })
                .collect(Collectors.toList());

        return applyVoList;
    }

}
