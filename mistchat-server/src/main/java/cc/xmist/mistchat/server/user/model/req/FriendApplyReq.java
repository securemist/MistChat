package cc.xmist.mistchat.server.user.model.req;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class FriendApplyReq {
    @NotNull
    private Long targetUid;
    private String msg;
}
