package cc.xmist.mistchat.server.user.model.vo;

import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FriendApplyVo {
    private Long applyId;
    @Schema(description = "申请人")
    private SummaryUser user;

    @Schema(description = "申请状态")
    private ApplyStatus status;
    @Schema(description = "申请信息")
    private String msg;
}
