package cc.xmist.mistchat.server.user.model.req;

import cc.xmist.mistchat.server.user.model.enums.WSRequestTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSBaseRequest {
    /**
     * @see WSRequestTypeEnum
     */
    private Integer type;
    private String data;
}
