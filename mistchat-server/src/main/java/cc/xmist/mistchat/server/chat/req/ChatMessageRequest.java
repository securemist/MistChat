package cc.xmist.mistchat.server.chat.req;

import cc.xmist.mistchat.server.common.enums.MessageType;
import lombok.Data;

@Data
public class ChatMessageRequest {
    protected MessageType type;
    protected Object body;
}
