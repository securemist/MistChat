package cc.xmist.mistchat.server.user.model.req;

import cc.xmist.mistchat.server.user.model.enums.BlackType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BlockReq {
    @NotBlank
    private Long uid;
    private BlackType type;
}
