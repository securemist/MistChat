package cc.xmist.mistchat.server.chat.model;

import cc.xmist.mistchat.server.common.enums.MessageType;
import lombok.Data;

@Data
public class ChatMessage {
    protected MessageType type;
    protected Object body;
}
