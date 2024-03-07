package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.chat.service.RoomService;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.user.UserAdapter;
import cc.xmist.mistchat.server.user.dao.UserApplyDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.dao.UserFriendDao;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import cc.xmist.mistchat.server.user.model.req.ApplyAddRequest;
import cc.xmist.mistchat.server.user.model.req.ApplyHandleRequest;
import cc.xmist.mistchat.server.user.model.vo.SummaryUser;
import cc.xmist.mistchat.server.user.model.vo.FriendApplyVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    public void createApply(Long uid, ApplyAddRequest request) {
        ApplyType type = request.getType();
        Long targetId = request.getTargetId();
        if (userApplyDao.existApply(uid, type, targetId)) {
            throw new BusinessException("请勿重复申请");
        }

        // TODO FRIEND
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
        checkApply(uid, apply);

        userApplyDao.handleApply(uid, apply, request.getPass(), request.getMsg());

        switch (apply.getType()) {
            case FRIEND -> friendApplyPass(uid, apply.getUid());
        }
    }

    private static void checkApply(Long uid, UserApply apply) {
        if (apply.getStatus().equals(ApplyStatus.PASS) || apply.getStatus().equals(ApplyStatus.FORBID)) {
            throw new BusinessException("请勿重复处理");
        }

        if (!apply.getTargetId().equals(uid)) {
            throw new BusinessException("异常操作");
        }
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
    public List<FriendApplyVo> getApplyList(Long uid) {
        // 所有申请
        List<UserApply> applyList = userApplyDao.getReceivedApplyList(uid);
        // 申请者id
        List<Long> applyUidList = applyList.stream()
                .map(apply -> apply.getUid())
                .collect(Collectors.toList());
        // 申请者信息map
        Map<Long, SummaryUser> userIdMap =
                userService.getSummaryUsers(applyUidList).stream()
                        .collect(Collectors.toMap(user -> user.getUid(), Function.identity()));
        
        List<FriendApplyVo> applyVoList = applyList.stream()
                .map(apply -> {
                    return FriendApplyVo.builder()
                            .applyId(apply.getId())
                            .status(apply.getStatus())
                            .msg(apply.getMsg())
                            .user(userIdMap.get(apply.getUid()))
                            .build();
                })
                .collect(Collectors.toList());

        return applyVoList;
    }
}
