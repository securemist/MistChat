package cc.xmist.mistchat.server.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CursorResult<T> {
    private String cursor;
    private Boolean isLast = Boolean.FALSE;
    private List<T> list;
}