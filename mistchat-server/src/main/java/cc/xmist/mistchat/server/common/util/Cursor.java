package cc.xmist.mistchat.server.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class Cursor<T> {
    private Integer pageSize;
    private T value;
    private Class<T> type;

    public static Cursor<Integer> buildInteger(String value, Integer pageSize) {
        Cursor<Integer> c = new Cursor<>();
        c.type = Integer.class;
        c.value = StrUtil.isNotBlank(value) ? Integer.valueOf(value) : null;
        return c;
    }
}
