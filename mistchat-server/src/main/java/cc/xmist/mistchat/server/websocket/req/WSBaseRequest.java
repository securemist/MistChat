package cc.xmist.mistchat.server.websocket.req;

import cc.xmist.mistchat.server.websocket.enums.WSRequestType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSBaseRequest {
    /**
     * @see WSRequestType
     */
    private Integer type;
    private String data;
}
