package cc.xmist.mistchat.server.chat.resp;

import cc.xmist.mistchat.server.chat.entity.MessageExtra;
import cc.xmist.mistchat.server.common.enums.MessageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {

    @Data
    @Builder
    public static class Message {
        private Long id;
        private Long uid;
        private Long roomId;
        private String content;
        private Long replyMsgId;
        private Long replyGap;
        private MessageType type;
        private MessageExtra extra;
        private LocalDateTime createTime;
    }

    public static Message build(cc.xmist.mistchat.server.chat.entity.Message m) {
        return Message.builder()
                .id(m.getId())
                .uid(m.getUid())
                .roomId(m.getRoomId())
                .content(m.getContent())
                .replyMsgId(m.getReplyMsgId())
                .replyGap(m.getReplyGap())
                .type(m.getType())
                .extra(m.getExtra())
                .createTime(m.getCreateTime())
                .build();
    }
}
