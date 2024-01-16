package cc.xmist.mistchat.server.user.model.resp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WSBaseResponse<T> {
    private Integer type;
    private T data;
}
