package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.UserOnlineEvent;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cc.xmist.mistchat.server.socketio.ClientPool;
import cc.xmist.mistchat.server.socketio.EventEmitter;
import cc.xmist.mistchat.server.socketio.enums.SEvent;
import cc.xmist.mistchat.server.socketio.event.OnlineEvent;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.model.resp.UserInfoVo;
import cc.xmist.mistchat.server.user.service.IpService;
import cc.xmist.mistchat.server.user.service.UserService;
import cn.hutool.core.collection.CollectionUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserOnlineListener {
    private final UserDao userDao;
    private final IpService ipService;
    private final UserService userService;
    private final GroupMemberDao groupMemberDao;
    private final EventEmitter eventEmitter;

    @Value("${config.ip.parse-when-login}")
    private Boolean parseWhenLogin;

    @EventListener(classes = UserOnlineEvent.class)
    @Async
    public void groupPush(UserOnlineEvent event) {
        Long uid = event.getUid();
        List<Long> groupsId = groupMemberDao.getBelongingGroupsId(uid);

        // 用户加入的群聊的所有用户
        ArrayList<Long> users = new ArrayList<>();
        Map<Long, List<Long>> membersMap = groupMemberDao.getMembersBatch(groupsId);
        membersMap.forEach((k,v) -> {
            v.forEach(i -> users.add(i));
        });

        // TODO 只推送会话打开的用户

        eventEmitter.emits(users, new OnlineEvent(uid));
    }

    @EventListener(classes = UserOnlineEvent.class)
    public void parseIpInfo(UserOnlineEvent event) {
        Long uid = event.getUid();
        if (parseWhenLogin) ipService.updateIpInfo(uid, event.getIp());
    }
}
