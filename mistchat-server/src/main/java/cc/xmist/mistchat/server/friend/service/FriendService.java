package cc.xmist.mistchat.server.friend.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.service.ContactService;
import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import cc.xmist.mistchat.server.common.enums.ApplyType;
import cc.xmist.mistchat.server.common.event.FriendApplyEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.friend.dao.FriendApplyDao;
import cc.xmist.mistchat.server.friend.dao.FriendDao;
import cc.xmist.mistchat.server.friend.entity.Friend;
import cc.xmist.mistchat.server.friend.entity.FriendApply;
import cc.xmist.mistchat.server.friend.req.FriendApplyHandleRequest;
import cc.xmist.mistchat.server.friend.req.FriendApplyRequest;
import cc.xmist.mistchat.server.friend.resp.FriendApplyHandleResponse;
import cc.xmist.mistchat.server.friend.resp.FriendApplyResponse;
import cc.xmist.mistchat.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendService {
    private final FriendDao friendDao;
    private final UserService userService;
    private final FriendApplyDao friendApplyDao;
    private final ContactDao contactDao;
    private final ApplicationEventPublisher eventPublisher;
    private final ContactService contactService;


    /**
     * 获取好友列表
     *
     * @param uid
     * @return
     */
    public List<Long> getFriendIdList(Long uid) {
        return friendDao.listFriendsId(uid);
    }

    @Transactional(rollbackFor = Exception.class)
    public FriendApplyHandleResponse apply(Long uid, FriendApplyRequest req) {
        Long targetUid = req.getTargetUid();
        if (uid == targetUid) throw new ParamException();
        if (friendDao.isFriend(uid, targetUid)) throw new BusinessException("你们已经是好友了，请勿重复添加");
        if (friendApplyDao.get(uid, ApplyType.FRIEND, targetUid) != null) throw new BusinessException("请勿重复申请");

        // 在收到对方的好友申请时，发现自己之前向对方发出过好友申请，视为直接同意申请
        FriendApply apply = friendApplyDao.get(targetUid, ApplyType.FRIEND, uid);
        if (apply != null) {
            apply = friendApplyDao.handle(apply, true, null);
            Contact contact = friendApplyPass(apply);
            return FriendApplyHandleResponse.build(apply, contact);
        }

        apply = friendApplyDao.addFriendApply(uid, targetUid, req.getMsg());
        return FriendApplyHandleResponse.build(apply, null);
    }

    public Contact friendApplyPass(FriendApply apply) {
        Long uid = apply.getUid();
        Long targetUid = apply.getTargetUid();

        // 创建好友表记录
        Friend friend = friendDao.create(uid, targetUid);
        // 初始化会话信息
        contactDao.initFriend(friend);

        eventPublisher.publishEvent(new FriendApplyEvent(this, apply));

        return contactService.getContact(uid, friend.getRoomId());
    }


    /**
     * 处理好友申请
     *
     * @param uid
     * @param req
     * @return
     */
    public FriendApplyHandleResponse handleApply(Long uid, FriendApplyHandleRequest req) {
        FriendApply apply = friendApplyDao.getById(req.getApplyId());

        if (apply == null || !apply.getTargetUid().equals(uid)) throw new ParamException();
        if (apply.getStatus().equals(ApplyStatus.PASS) || apply.getStatus().equals(ApplyStatus.FORBID))
            throw new BusinessException("请勿重复处理");

         apply = friendApplyDao.handle(apply, req.getPass(), req.getMsg());
        if (req.getPass()) {
            Contact contact = friendApplyPass(apply);
        return FriendApplyHandleResponse.build(apply, contact);
        };
        return FriendApplyHandleResponse.build(apply,null);
    }


    public FriendApplyResponse getApplyList(Long uid) {
        List<FriendApply> applyList = friendApplyDao.list(uid);
        return FriendApplyResponse.build(uid, applyList);
    }
}
