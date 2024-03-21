package cc.xmist.mistchat.server.user.resp;

import cc.xmist.mistchat.server.common.enums.Gender;
import cc.xmist.mistchat.server.common.enums.Role;
import cc.xmist.mistchat.server.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "用户信息")
public class UserResponse {
    @Schema(description = "用户id")
    private Long id;
    @Schema(description = "用户名称")
    private String name;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "性别")
    private Gender gender;
    @Schema(description = "ip属地")
    private String location;
    @Schema(description = "角色")
    private Role role;

    public static UserResponse build(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .gender(u.getGender())
                .avatar(u.getAvatar())
                .name(u.getName())
                .role(u.getRole())
                .location(u.getIpInfo() != null ? u.getIpInfo().getLastIpDetail().getCity() : null)
                .build();
    }
}
