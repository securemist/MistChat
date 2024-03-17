package cc.xmist.mistchat.server.user.model.req;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class GroupApplyReq {
    @NotNull
    private Long groupId;
    private String msg;
}
