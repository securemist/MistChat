package cc.xmist.mistchat.server.chat.model.resp;

import cc.xmist.mistchat.server.chat.model.enums.MessageType;
import cc.xmist.mistchat.server.chat.model.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {
    private UserInfo fromUser;
    private Message message;
    @Data
    @Builder
    public static class UserInfo{
        private Long uid;
    }

    @Data
    @Builder
    public static class Message{
        private Long uid;
        private Long roomId;
//        private RoomType roomType;
        private LocalDateTime sendTime;
        private MessageType type;
        private Object body;
    }
}
