package cc.xmist.mistchat.server.friend.req;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class FriendApplyRequest {
    @NotNull
    private Integer targetUid;
    private String msg;
}
