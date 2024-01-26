package cc.xmist.mistchat.server.chat.message.req;

import lombok.Data;

@Data
public class TextMessageRequest implements MessageRequest {
    private String content;
}
