package cc.xmist.mistchat.server.user.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadgeVo {
    @Schema(description = "徽章id")
    private Long id;
    @Schema(description = "图片url")
    private String img;
    @Schema(description = "徽章描述")
    private String description;
    @Schema(description = "是否持有")
    private Boolean own;
    @Schema(description = "是否佩戴")
    private Boolean wearing;
}
