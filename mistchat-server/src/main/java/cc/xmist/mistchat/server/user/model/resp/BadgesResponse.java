package cc.xmist.mistchat.server.user.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BadgesResponse {

    @Schema(description = "用户佩戴的徽章id")
    private Long usedBadgeId;
    @Schema(description = "用户id")
    private Long uid;
    @Schema(description = "徽章信息")
    private List<Badge> badges;

    @Data
    @Builder
    public static class Badge {
        @Schema(description = "徽章id")
        private Long id;
        @Schema(description = "图片url")
        private String img;
        @Schema(description = "徽章描述")
        private String description;
        @Schema(description = "是否持有")
        private Boolean own;
    }
}
