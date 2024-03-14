package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.chat.model.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.model.dao.GroupContactDao;
import cc.xmist.mistchat.server.chat.model.dao.GroupMemberDao;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.ChatType;
import cc.xmist.mistchat.server.chat.service.ContactService;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import cc.xmist.mistchat.server.socketio.SocketService;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageSendListener {
    @Resource
    private SocketService socketService;
    @Resource
    private ContactService contactService;
    @Resource
    private GroupMemberDao groupMemberDao;
    @Resource
    private GroupContactDao groupContactDao;
    @Resource
    private FriendContactDao friendContactDao;

    @EventListener(MessageSendEvent.class)
    public void send(MessageSendEvent event) {
        Message message = event.getMessage();
        List<Long> targetIds = new ArrayList();
        Long uid = message.getUid();

        if (event.getChatType() == ChatType.FRIEND) {
            targetIds = Arrays.asList(event.getChatId());
        } else {
            // 获取群所有成员
            targetIds = groupMemberDao.getMembers(event.getChatId());
        }

        switch (event.getChatType()) {
            case FRIEND: {
                socketService.sendToUser(targetIds.get(0), message);
            }
            case GROUP: {
                socketService.sendToGroup(targetIds, message);
            }
        }
    }

    @EventListener(MessageSendEvent.class)
    public void updateContact(MessageSendEvent event) {
        Message message = event.getMessage();
        Long chatId = event.getChatId();
        ChatType chatType = event.getChatType();

        List<Long> targetIds = new ArrayList();
        Long uid = message.getUid();

        switch (chatType) {
            case FRIEND: {
//                contactService.updateFriendContact(uid,chatId, message.getId());
            }
            case GROUP: {
                socketService.sendToGroup(targetIds, message);
            }
        }
    }
}
