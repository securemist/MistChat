package cc.xmist.mistchat.server.chat.resp;

import cc.xmist.mistchat.server.common.enums.RoomType;
import cc.xmist.mistchat.server.common.enums.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {
    private Long uid;
    private RoomType type;
    private Message message;

    @Data
    @Builder
    public static class Message {
        private Long id;
        private Long roomId;
        private RoomType roomType;
        private LocalDateTime sendTime;
        private MessageType type;
        private Object body;
    }
}
