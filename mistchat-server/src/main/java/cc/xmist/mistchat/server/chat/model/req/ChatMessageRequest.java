package cc.xmist.mistchat.server.chat.model.req;

import cc.xmist.mistchat.server.chat.model.enums.MessageType;
import lombok.Data;

@Data
public class ChatMessageRequest {
    private Long roomId;
    private MessageType type;
    private Object body;
}
