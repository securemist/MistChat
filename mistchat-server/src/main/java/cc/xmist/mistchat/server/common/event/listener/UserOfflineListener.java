package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.enums.ActiveStatus;
import cc.xmist.mistchat.server.common.event.UserOfflineEvent;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cc.xmist.mistchat.server.socketio.EventEmitter;
import cc.xmist.mistchat.server.socketio.event.ActiveEvent;
import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserOfflineListener {
    private final GroupMemberDao groupMemberDao;
    private final EventEmitter eventEmitter;

    @EventListener(UserOfflineEvent.class)
    public void push(UserOfflineEvent event) {
        Long uid = event.getUid();
        List<Long> groupsId = groupMemberDao.getBelongingGroupsId(uid);
        if (CollectionUtil.isEmpty(groupsId)) return;

        // 用户加入的群聊的所有用户
        ArrayList<Long> users = new ArrayList<>();
        Map<Long, List<Long>> membersMap = groupMemberDao.getMembersBatch(groupsId);
        membersMap.forEach((k, v) -> {
            v.forEach(i -> users.add(i));
        });

        // TODO 只推送会话打开的用户

        eventEmitter.emits(users, new ActiveEvent(uid, ActiveStatus.OFF));
    }
}
