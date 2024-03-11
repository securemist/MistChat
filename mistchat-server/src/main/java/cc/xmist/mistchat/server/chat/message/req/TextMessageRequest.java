package cc.xmist.mistchat.server.chat.message.req;

import cc.xmist.mistchat.server.chat.model.ChatMessage;
import lombok.Data;

@Data
public class TextMessageRequest extends ChatMessage{
    private String content;
}
