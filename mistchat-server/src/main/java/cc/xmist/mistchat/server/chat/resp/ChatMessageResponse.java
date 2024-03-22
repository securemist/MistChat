package cc.xmist.mistchat.server.chat.resp;

import cc.xmist.mistchat.server.common.enums.MessageType;
import cc.xmist.mistchat.server.common.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {
    private Integer uid;
    private RoomType type;
    private Message message;

    @Data
    @Builder
    public static class Message {
        private Integer id;
        private Integer roomId;
        private RoomType roomType;
        private LocalDateTime sendTime;
        private MessageType type;
        private Object body;
    }
}
