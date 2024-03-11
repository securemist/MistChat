package cc.xmist.mistchat.server.user.model.req;

import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplyHandleReq {
    private Long applyId;
    private ApplyType type;
    @NotNull
    private Boolean pass;
    private String msg;
}
