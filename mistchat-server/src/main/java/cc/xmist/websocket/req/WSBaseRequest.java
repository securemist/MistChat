package cc.xmist.websocket.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSBaseRequest {
    /**
     * @see cc.xmist.websocket.enums.WSRequestType
     */
    private Integer type;
    private String data;
}
