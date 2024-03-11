package cc.xmist.mistchat.server.common.event;

import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.ChatType;
import cc.xmist.mistchat.server.chat.service.ChatService;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageSendEvent extends ApplicationEvent {
    private Message message;
    private Long targetId; // 单聊为对方 uid，群聊为 groupId
    private ChatType chatType;

    public MessageSendEvent(ChatService source, ChatType type, Long targetId,Message message) {
        super(source);
        this.message = message;
        this.targetId = targetId;
        this.chatType = type;
    }
}
