package cc.xmist.mistchat.server.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class Cursor<T> {
    private Long pageSize;
    private T value;
    private Class<T> type;

    public static Cursor<Long> buildLong(String value, Integer pageSize) {
        Cursor<Long> c = new Cursor<>();
        c.type = Long.class;
        c.value = StrUtil.isNotBlank(value) ? Long.valueOf(value) : null;
        return c;
    }
}
