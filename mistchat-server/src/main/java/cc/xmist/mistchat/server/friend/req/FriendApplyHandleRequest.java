package cc.xmist.mistchat.server.friend.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendApplyHandleRequest {
    private Integer applyId;
    @NotNull
    private Boolean pass;
    private String msg;
}
