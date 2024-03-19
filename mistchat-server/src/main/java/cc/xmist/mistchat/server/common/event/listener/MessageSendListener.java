package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.service.ContactService;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cc.xmist.mistchat.server.socketio.EventEmitter;
import cc.xmist.mistchat.server.socketio.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
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
    private final ContactDao contactDao;

    @EventListener(MessageSendEvent.class)
    public void send(MessageSendEvent event) {
        Message message = event.getMessage();

        Contact contact = event.getContact();
        List<Long> uids = switch (contact.getChatType()) {
            case FRIEND -> Arrays.asList(contact.getChatId());
            case GROUP -> groupMemberDao.getMembers(contact.getChatId());
        };

        MessageEvent.Data data = new MessageEvent.Data();
        data.setMessage(message);

        eventEmitter.emits(uids, new MessageEvent(data));
    }

    @EventListener(MessageSendEvent.class)
    @Async
    public void updateContact(MessageSendEvent event) {
        Message message = event.getMessage();
        Long uid = event.getUid();
        Contact contact = event.getContact();

        contactService.updateContact(uid, contact, message.getId());
    }
}
