package cc.xmist.mistchat.server.user.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WearingBadgeVo {
    @Schema(description = "用户 id")
    private Long uid;
    @Schema(description = "徽章 id")
    private Long itemId;
    @Schema(description = "图片url")
    private String img;
    @Schema(description = "徽章描述")
    private String description;
}
