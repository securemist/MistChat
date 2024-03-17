package cc.xmist.mistchat.server.user.model.resp;

import cc.xmist.mistchat.server.friend.entity.FriendApply;
import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendApplyResp {
    private Long applyId;
    private ApplyStatus status;

    public static FriendApplyResp build(FriendApply apply) {
        return FriendApplyResp.builder()
                .applyId(apply.getId())
                .status(apply.getStatus())
                .build();
    }
}