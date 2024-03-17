package cc.xmist.mistchat.server.chat.model.resp;

import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.enums.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {
    private Long uid;
    private ChatType type;
    private Message message;

    @Data
    @Builder
    public static class Message {
        private Long id;
        private Long roomId;
        private ChatType roomType;
        private LocalDateTime sendTime;
        private MessageType type;
        private Object body;
    }
}
