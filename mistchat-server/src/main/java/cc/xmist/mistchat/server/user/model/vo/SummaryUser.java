package cc.xmist.mistchat.server.user.model.vo;

import cc.xmist.mistchat.server.user.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryUser {
    private Long uid;
    @Schema(description = "名字")
    private String name;
    @Schema(description = "邮箱")
    private String avatar;
    @Schema(description = "性别")
    private Gender gender;
    @Schema(description = "ip属地")
    private String location;
    @Schema(description = "穿戴的徽章")
    private Badge wearingBadge;
    @Data
    @Builder
    public static class Badge {
        @Schema(description = "物品id")
        private Long itemId;
        @Schema(description = "图片")
        private String img;
        @Schema(description = "描述")
        private String description;
    }
}
