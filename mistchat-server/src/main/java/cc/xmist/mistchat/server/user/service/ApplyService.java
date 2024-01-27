package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.chat.service.RoomService;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.user.dao.UserApplyDao;
import cc.xmist.mistchat.server.user.dao.UserFriendDao;
import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatusType;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import cc.xmist.mistchat.server.user.model.req.ApplyAddRequest;
import cc.xmist.mistchat.server.user.model.req.ApplyHandleRequest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ApplyService {
    @Resource
    private UserApplyDao userApplyDao;
    @Resource
    private RoomService roomService;
    @Resource
    private UserFriendDao userFriendDao;

    /**
     * 创建申请
     *
     * @param uid
     * @param request
     */
    public void createApply(Long uid, ApplyAddRequest request) {
        ApplyType type = request.getType();
        Long targetId = request.getTargetId();
        if (userApplyDao.existApply(uid, type, targetId)) {
            throw new BusinessException("请勿重复申请");
        }
        switch (type) {
            case FRIEND -> createFriendApply(uid, targetId, request.getMsg());
        }
    }

    private void createFriendApply(Long uid, Long targetId, String msg) {
        if (isFriend(uid, targetId)) {
            throw new BusinessException("你们已经是好友了，请勿重复添加");
        }
        userApplyDao.addFriendApply(uid, targetId, msg);
    }

    public Boolean isFriend(Long uid, Long friendId) {
        return userFriendDao.isFriend(uid, friendId);
    }

    /**
     * 处理请求
     *
     * @param uid
     * @param request
     */
    public void handleApply(Long uid, ApplyHandleRequest request) {
        UserApply apply = userApplyDao.getById(request.getApplyId());
        if (apply.getStatus().equals(ApplyStatusType.PASS) || apply.getStatus().equals(ApplyStatusType.FORBID)) {
            throw new BusinessException("请勿重复处理");
        }

        userApplyDao.handleApply(uid, apply, request.getPass(), request.getMsg());

        ApplyType applyType = apply.getType();

        switch (applyType) {
            case FRIEND -> friendApplyPass(uid, apply.getTargetId());
        }
    }

    private Long friendApplyPass(Long uid, Long targetId) {
        userFriendDao.create(uid, targetId);
        Long roomId = roomService.createFriendRoom(uid, targetId);
        return roomId;
    }


}
