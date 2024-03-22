package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.enums.Groups;
import cc.xmist.mistchat.server.common.event.UserRegisterEvent;
import cc.xmist.mistchat.server.group.sevrice.GroupMemberService;
import cc.xmist.mistchat.server.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserRegisterListener {
    private final UserDao userDao;
    private final GroupMemberService groupMemberService;

    @TransactionalEventListener(classes = UserRegisterEvent.class)
    @Async
    public void joinGroup(UserRegisterEvent event) {
        // 注册成功加入所有群聊
        cc.xmist.mistchat.server.user.model.entity.User user = event.getUser();
        Integer uid = user.getId();
        groupMemberService.join(uid, Groups.ALL_USERS_GROUP.getId());
    }
}
