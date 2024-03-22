package cc.xmist.mistchat.server.user.model.req;

import cc.xmist.mistchat.server.common.enums.BlackType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BlockRequest {
    @NotBlank
    private Integer uid;
    private BlackType type;
}
