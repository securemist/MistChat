package cc.xmist.mistchat.server.chat.resp;

import cc.xmist.mistchat.server.common.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContactResponse {
    private List<ContactResponse.Contact> list;

    @Data
    @Builder
    public static class Contact {
        private Long id;
        private Long uid;
        private RoomType chatType;
        private Long chatId;
        private Long readMsgId;
        private Long activeMsgId;
        private Long unReadCount;
    }
}
