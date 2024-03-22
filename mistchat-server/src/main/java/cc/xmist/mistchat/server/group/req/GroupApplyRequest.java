package cc.xmist.mistchat.server.group.req;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class GroupApplyRequest {
    @NotNull
    private String roomId;
    private String msg;
}
