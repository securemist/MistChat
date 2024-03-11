package cc.xmist.mistchat.server.common.event;

import cc.xmist.mistchat.server.chat.model.ChatMessage;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.MessageType;
import cc.xmist.mistchat.server.chat.model.enums.RoomType;
import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.chat.service.ChatService;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class MessageSendEvent extends ApplicationEvent {
    private Message message;
    private RoomType type;

    public MessageSendEvent(ChatService source, RoomType type, Message message) {
        super(source);
        this.message = message;
        this.type = type;
    }
}
