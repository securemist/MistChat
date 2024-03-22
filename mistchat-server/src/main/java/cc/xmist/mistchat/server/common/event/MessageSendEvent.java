package cc.xmist.mistchat.server.common.event;

import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.service.MessageService;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageSendEvent extends ApplicationEvent {
    private Message message;
    private Integer uid;
    private Contact contact;

    public MessageSendEvent(MessageService source, Integer uid, Contact contact, Message message) {
        super(source);
        this.message = message;
        this.uid = uid;
        this.contact = contact;
    }
}
