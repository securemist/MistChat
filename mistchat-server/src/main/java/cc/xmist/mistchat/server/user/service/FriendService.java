package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.chat.model.dao.FriendContactDao;
import cc.xmist.mistchat.server.common.event.FriendApplyEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.user.dao.FriendApplyDao;
import cc.xmist.mistchat.server.user.dao.FriendDao;
import cc.xmist.mistchat.server.user.model.entity.Friend;
import cc.xmist.mistchat.server.user.model.entity.FriendApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import cc.xmist.mistchat.server.user.model.req.FriendApplyHandleReq;
import cc.xmist.mistchat.server.user.model.req.FriendApplyReq;
import cc.xmist.mistchat.server.user.model.resp.ForwardApplyVo;
import cc.xmist.mistchat.server.user.model.resp.FriendApplyResp;
import cc.xmist.mistchat.server.user.model.resp.ReceivedApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendService {
    private FriendDao friendDao;
    private UserService userService;
    private FriendApplyDao friendApplyDao;
    private FriendContactDao friendContactDao;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public FriendService(FriendDao friendDao, UserService userService, FriendApplyDao friendApplyDao, FriendContactDao friendContactDao, ApplicationEventPublisher eventPublisher) {
        this.friendDao = friendDao;
        this.userService = userService;
        this.friendApplyDao = friendApplyDao;
        this.friendContactDao = friendContactDao;
        this.eventPublisher = eventPublisher;
    }


    /**
     * 获取好友列表
     *
     * @param uid
     * @return
     */
    public List<Long> getFriendIdList(Long uid) {
        return friendDao.getFriendIdList(uid);
    }

    public FriendApplyResp apply(Long uid, FriendApplyReq req) {
        Long targetUid = req.getTargetUid();
        if (uid == targetUid) throw new ParamException();
        if (friendDao.isFriend(uid, targetUid)) throw new BusinessException("你们已经是好友了，请勿重复添加");
        if (friendApplyDao.exist(uid, ApplyType.FRIEND, targetUid)) throw new BusinessException("请勿重复申请");

        FriendApply apply = friendApplyDao.find(targetUid, ApplyType.FRIEND, uid);
        if (apply != null) {
            friendApplyDao.handle(apply.getId(), true, null);
            friendApplyPass(uid, targetUid);
            return FriendApplyResp.build(apply);
        }

        apply = friendApplyDao.addFriendApply(uid, targetUid, req.getMsg());
        return FriendApplyResp.build(apply);
    }

    /**
     * uid 同意了 targetUid 的申请
     *
     * @param uid
     * @param targetUid
     * @return
     */
    private Long friendApplyPass(Long uid, Long targetUid) {
        Friend friend = friendDao.create(uid, targetUid);
        friendContactDao.firstCreate( uid, targetUid);
        eventPublisher.publishEvent(new FriendApplyEvent(uid, targetUid));
        return friend.getId();
    }


    /**
     * 处理好友申请
     * @param uid
     * @param req
     */
    public void handleApply(Long uid, FriendApplyHandleReq req) {
        FriendApply apply = friendApplyDao.getById(req.getApplyId());
        if (apply == null || !apply.getTargetUid().equals(uid)) throw new ParamException();
        if (apply.getStatus().equals(ApplyStatus.PASS) || apply.getStatus().equals(ApplyStatus.FORBID))
            throw new BusinessException("请勿重复处理");

        friendApplyDao.handle(apply.getId(), req.getPass(), req.getMsg());
        if (req.getPass()) {
            friendApplyPass(uid, apply.getTargetUid());
        }
    }

    /**
     * 获取好友申请列表
     *
     * @param uid
     * @return
     */
    public List<ReceivedApplyVo> getReceivedApplyList(Long uid) {
        // 所有申请
        List<FriendApply> applyList = friendApplyDao.getReceivedApplyList(uid);
        List<FriendApply> forwardApplyList = friendApplyDao.getForwardApplyList(uid);

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
        List<FriendApply> applyList = friendApplyDao.getForwardApplyList(uid);

        List<ForwardApplyVo> applyVoList = applyList.stream()
                .map(apply -> {
                    return ForwardApplyVo.builder()
                            .applyId(apply.getId())
                            .status(apply.getStatus())
                            .msg(apply.getApplyMsg())
                            .targetUserId(apply.getTargetUid())
                            .build();
                })
                .collect(Collectors.toList());

        return applyVoList;
    }
}
