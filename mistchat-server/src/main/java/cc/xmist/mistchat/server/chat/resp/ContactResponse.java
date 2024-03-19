package cc.xmist.mistchat.server.chat.resp;

import cc.xmist.mistchat.server.common.enums.ChatType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ContactResponse {
    private List<ContactResponse> list;

    @Data
    @Builder
    public static class Contact {
        private ChatType chatType;
        private Long chatId;
        private Long readMsgId;
        private Long lastMsgId;
        private Integer unReadCount;
        private LocalDateTime activeTime;
    }
}
