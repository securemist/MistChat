package cc.xmist.mistchat.server.user.model.resp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "用户信息")
public class UserInfoResponse {
    @Schema(description = "用户id")
    private Long id;
    @Schema(description = "用户名称")
    private String name;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "性别")
    private Integer sex;
    @Schema(description = "可改名次数")
    private Long modifyNameChance;
}
