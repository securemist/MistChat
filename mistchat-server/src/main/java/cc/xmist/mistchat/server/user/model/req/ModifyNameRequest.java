package cc.xmist.mistchat.server.user.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ModifyNameRequest {
    @NotBlank(message = "名称不能为空")
    @Length(max = 6,min = 1,message = "名称长度应当在1到6位之间")
    @Schema(description = "新的名称")
    private String name;
}
