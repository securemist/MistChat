package cc.xmist.mistchat.server.chat.resp;

import cc.xmist.mistchat.server.chat.entity.Message;
import lombok.Data;

@Data
public class MsgSendResponse {
    private Message message;
}
