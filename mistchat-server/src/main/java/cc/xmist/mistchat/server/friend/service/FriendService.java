package cc.xmist.mistchat.server.friend.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.dao.MessageDao;
import cc.xmist.mistchat.server.chat.service.MessageService;
import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import cc.xmist.mistchat.server.common.enums.ApplyType;
import cc.xmist.mistchat.server.common.event.FriendApplyEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.friend.dao.FriendApplyDao;
import cc.xmist.mistchat.server.friend.dao.FriendDao;
import cc.xmist.mistchat.server.friend.entity.Friend;
import cc.xmist.mistchat.server.friend.entity.FriendApply;
import cc.xmist.mistchat.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FriendService {
    private final FriendDao friendDao;
    private final UserService userService;
    private final FriendApplyDao friendApplyDao;
    private final MessageDao messageDao;
    private final ApplicationEventPublisher eventPublisher;
    private final ContactDao contactDao;


    /**
     * 获取好友列表
     *
     * @param uid
     * @return
     */
    public List<Long> getFriendIdList(Long uid) {
        return friendDao.getFriendIdList(uid);
    }

    public cc.xmist.mistchat.server.user.model.resp.FriendApplyResp apply(Long uid, cc.xmist.mistchat.server.user.model.req.FriendApplyReq req) {
        Long targetUid = req.getTargetUid();
        if (uid == targetUid) throw new ParamException();
        if (friendDao.isFriend(uid, targetUid)) throw new BusinessException("你们已经是好友了，请勿重复添加");
        if (friendApplyDao.exist(uid, ApplyType.FRIEND, targetUid)) throw new BusinessException("请勿重复申请");

        FriendApply apply = friendApplyDao.find(targetUid, ApplyType.FRIEND, uid);
        if (apply != null) {
            friendApplyDao.handle(apply.getId(), true, null);
            friendApplyPass(apply);
            return cc.xmist.mistchat.server.user.model.resp.FriendApplyResp.build(apply);
        }

        apply = friendApplyDao.addFriendApply(uid, targetUid, req.getMsg());
        return cc.xmist.mistchat.server.user.model.resp.FriendApplyResp.build(apply);
    }

    private void friendApplyPass(FriendApply apply) {
        Long uid = apply.getUid();
        Long targetUid = apply.getTargetUid();

        // 创建好友表记录
        Friend friend = friendDao.create(uid, targetUid);

        // 创建两人的会话
        Long[] contactIds = contactDao.initOnFriend(uid, targetUid);

        // 发送两人成为好友的系统消息
        Long[] msgIds = messageDao.initOnFriend(uid, contactIds[0], targetUid, contactIds[1]);
        // 更新两人会话的 lasg_msg_id
        contactDao.initLastMsgId(contactIds[0], msgIds[0], contactIds[1], msgIds[1]);

        eventPublisher.publishEvent(new FriendApplyEvent(this, apply)); // TODO
    }


    /**
     * 处理好友申请
     *
     * @param uid
     * @param req
     */
    public void handleApply(Long uid, cc.xmist.mistchat.server.user.model.req.FriendApplyHandleReq req) {
        FriendApply apply = friendApplyDao.getById(req.getApplyId());
        if (apply == null || !apply.getTargetUid().equals(uid)) throw new ParamException();
        if (apply.getStatus().equals(ApplyStatus.PASS) || apply.getStatus().equals(ApplyStatus.FORBID))
            throw new BusinessException("请勿重复处理");

        friendApplyDao.handle(apply.getId(), req.getPass(), req.getMsg());
        if (req.getPass()) {
            friendApplyPass(apply);
        }
    }

    /**
     * 获取好友申请列表
     *
     * @param uid
     * @return
     */
    public List<cc.xmist.mistchat.server.user.model.resp.ReceivedApplyVo> getReceivedApplyList(Long uid) {
        // 所有申请
        List<FriendApply> applyList = friendApplyDao.getReceivedApplyList(uid);
        List<FriendApply> forwardApplyList = friendApplyDao.getForwardApplyList(uid);

        List<cc.xmist.mistchat.server.user.model.resp.ReceivedApplyVo> applyVoList = applyList.stream()
                .map(apply -> {
                    return cc.xmist.mistchat.server.user.model.resp.ReceivedApplyVo.builder()
                            .applyId(apply.getId())
                            .status(apply.getStatus())
                            .msg(apply.getApplyMsg())
                            .applyUserId(apply.getUid())
                            .build();
                })
                .collect(Collectors.toList());

        return applyVoList;
    }

    public List<cc.xmist.mistchat.server.user.model.resp.ForwardApplyVo> getForwardApplyList(Long uid) {
        List<FriendApply> applyList = friendApplyDao.getForwardApplyList(uid);

        List<cc.xmist.mistchat.server.user.model.resp.ForwardApplyVo> applyVoList = applyList.stream()
                .map(apply -> {
                    return cc.xmist.mistchat.server.user.model.resp.ForwardApplyVo.builder()
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
