package cc.xmist.mistchat.server.chat.message.extra;

import cc.xmist.mistchat.server.common.enums.SystemMsgType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
public class SystemMsgExtra {
    private SystemMsgType type;
}
