package cc.xmist.mistchat.server.chat.message.extra;

import cc.xmist.mistchat.server.common.enums.SystemMsgType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemMsgExtra {
    private SystemMsgType type;
}
