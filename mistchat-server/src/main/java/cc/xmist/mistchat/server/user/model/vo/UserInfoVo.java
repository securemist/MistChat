package cc.xmist.mistchat.server.user.model.vo;

import cc.xmist.mistchat.server.user.model.enums.RoleType;
import cc.xmist.mistchat.server.user.model.enums.SexType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "用户信息")
public class UserInfoVo {
    @Schema(description = "用户id")
    private Long id;
    @Schema(description = "用户名称")
    private String name;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "性别")
    private SexType sex;
    @Schema(description = "可改名次数")
    private Long modifyNameChance;
    @Schema(description = "角色")
    private Integer role;
}
