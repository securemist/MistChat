package cc.xmist.mistchat.server.chat.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ContactResponse {

    @Data
    @Builder
    public static class Contact {
        private Long id;
        private Long uid;
        private Long roomId;
        private Long readMsgId;
        private Long activeMsgId;
        private Long unReadCount;
        private LocalDateTime createTime;
    }

    public static Contact build(cc.xmist.mistchat.server.chat.entity.Contact c) {
        return Contact.builder()
                .id(c.getId())
                .roomId(c.getRoomId())
                .readMsgId(c.getReadMsgId())
                .activeMsgId(c.getActiveMsgId())
                .unReadCount(c.getUnreadCount())
                .createTime(c.getCreateTime())
                .build();
    }
}
