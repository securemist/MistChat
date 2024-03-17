package cc.xmist.mistchat.server.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CursorResult<T> {
    @Schema(name = "游标")
    private String cursor;
    @Schema(name = "是否是最后一页")
    private Boolean isLast = Boolean.FALSE;
    @Schema(name = "数据")
    private List<T> list;
}
