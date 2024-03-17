package cc.xmist.mistchat.server.chat.model.req;

import cc.xmist.mistchat.server.chat.model.ChatMessage;
import lombok.Data;

@Data
public class GroupMessageReq {
    private Long groupId;
    private ChatMessage message;
}
