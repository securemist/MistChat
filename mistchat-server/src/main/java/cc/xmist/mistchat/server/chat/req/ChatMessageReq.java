package cc.xmist.mistchat.server.chat.model.req;

import cc.xmist.mistchat.server.chat.model.ChatMessage;
import cc.xmist.mistchat.server.common.enums.ChatType;
import lombok.Data;

@Data
public class ChatMessageReq {
    private Long targetId;
    private ChatType chatType;
    private ChatMessage message;
}
