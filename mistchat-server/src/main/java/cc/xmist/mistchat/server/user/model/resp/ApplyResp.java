package cc.xmist.mistchat.server.user.model.resp;

import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplyResp {
    private Long applyId;
    private ApplyType type;
    private ApplyStatus status;

    public static ApplyResp build(UserApply apply) {
        return ApplyResp.builder()
                .applyId(apply.getId())
                .type(ApplyType.FRIEND)
                .status(apply.getStatus())
                .build();
    }
}