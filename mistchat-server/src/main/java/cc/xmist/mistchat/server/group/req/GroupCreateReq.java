package cc.xmist.mistchat.server.group.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class GroupCreateReq {
    @NotBlank
    @Schema(description = "用户名")
    private String name;

    @Length(min = 0)
    @Schema(description = "用户列表")
    private List<Long> uidList;
}
