package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.chat.service.RoomService;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.user.dao.UserApplyDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.dao.UserFriendDao;
import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import cc.xmist.mistchat.server.user.model.req.ApplyAddReq;
import cc.xmist.mistchat.server.user.model.req.ApplyHandleReq;
import cc.xmist.mistchat.server.user.model.vo.ForwardApplyVo;
import cc.xmist.mistchat.server.user.model.vo.ReceivedApplyVo;
import jakarta.annotation.Resource;
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
    private UserService userService;

    /**
     * 创建申请
     *
     * @param uid
     * @param request
     */
    public void createApply(Long uid, ApplyAddReq request) {
        ApplyType type = request.getType();
        Long targetId = request.getTargetId();

        if (uid == targetId) throw new ParamException();
        if (userApplyDao.isFriend(uid, targetId)) throw new BusinessException("你们已经是好友了，请勿重复添加");
        if (userApplyDao.exist(uid, type, targetId)) throw new BusinessException("请勿重复申请");

        // 添加对方好友时，已经有对方添加自己的申请，视为同一申请
        UserApply apply = userApplyDao.find(targetId, type, uid);
        if (apply != null) {
            userApplyDao.handleApply(apply.getId(), true, null);
            userFriendDao.create(uid, targetId);
            return;
        }

        // TODO FRIEND
        switch (type) {
            case FRIEND -> {
                userApplyDao.addFriendApply(uid, targetId, request.getMsg());
                userFriendDao.create(uid, targetId);
            }
        }
    }


    /**
     * 处理请求
     *
     * @param uid
     * @param request
     */
    public void handleApply(Long uid, ApplyHandleReq request) {
        UserApply apply = userApplyDao.getById(request.getApplyId());
        checkApply(uid, apply);

        userApplyDao.handleApply(apply.getId(), request.getPass(), request.getMsg());

        switch (apply.getType()) {
            case FRIEND -> friendApplyPass(uid, apply.getUid());
        }
    }

    private static void checkApply(Long uid, UserApply apply) {
        if (apply == null) throw new ParamException();

        if (apply.getStatus().equals(ApplyStatus.PASS) || apply.getStatus().equals(ApplyStatus.FORBID))
            throw new BusinessException("请勿重复处理");

        if (!apply.getTargetId().equals(uid)) throw new BusinessException("异常操作");
    }

    private Long friendApplyPass(Long uid1, Long uid2) {
        userFriendDao.create(uid1, uid2);
        Long roomId = roomService.createFriendRoom(uid1, uid2);
        return roomId;
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
