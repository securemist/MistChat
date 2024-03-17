package cc.xmist.mistchat.server.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CursorReq {
    @Schema(name = "游标，每次请求都要带上，初始未 null")
    private String cursor;

    @Schema(name = "页面大小")
    @Min(0)
    @Max(100)
    private Integer size;
}
