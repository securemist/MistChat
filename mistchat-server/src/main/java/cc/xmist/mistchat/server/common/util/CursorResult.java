package cc.xmist.mistchat.server.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CursorResult<T> {
    @Schema(nullable = true)
    private String cursor;
    private Boolean isLast = Boolean.FALSE;
    private List<T> list;
}