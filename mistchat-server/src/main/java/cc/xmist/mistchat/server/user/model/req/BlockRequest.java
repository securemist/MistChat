package cc.xmist.mistchat.server.user.model.req;

import jakarta.validation.Valid;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class BlockRequest {
    @NotBlank
    private Long uid;
    @Range(min = 0,max = 1)
    private Integer type;
}
