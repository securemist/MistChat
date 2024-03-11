package cc.xmist.mistchat.server.user.model.req;

import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplyAddReq {
    @NotNull
    private ApplyType type;
    @NotNull
    private Long targetId;
    private String msg;
}
