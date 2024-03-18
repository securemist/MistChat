package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.chat.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.dao.GroupContactDao;
import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.service.ContactService;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cc.xmist.mistchat.server.socketio.EventEmitter;
import cc.xmist.mistchat.server.socketio.event.MessageEvent;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageSendListener {
    private final EventEmitter eventEmitter;
    private final ContactService contactService;
    private final GroupMemberDao groupMemberDao;
    private final GroupContactDao groupContactDao;
    private final FriendContactDao friendContactDao;

    @EventListener(MessageSendEvent.class)
    public void send(MessageSendEvent event) {
        Message message = event.getMessage();
        List<Long> uids = new ArrayList();
        Long uid = message.getUid();

        if (event.getChatType() == ChatType.FRIEND) {
            uids = Arrays.asList(event.getChatId());
        } else {
            // 获取群所有成员
            uids = groupMemberDao.getMembers(event.getChatId());
        }

        MessageEvent.Data data = new MessageEvent.Data();
        data.setMessage(message);

        eventEmitter.emits(uids, new MessageEvent(data));
    }

    @EventListener(MessageSendEvent.class)
    public void updateContact(MessageSendEvent event) {
        Message message = event.getMessage();
        Long chatId = event.getChatId();
        ChatType chatType = event.getChatType();

        Long uid = message.getUid();

        switch (chatType) {
            case FRIEND: contactService.updateFriendContact(uid,chatId, message);
            case GROUP: contactService.updateGroupContact(uid,chatId, message);
        }
    }
}
