package cc.xmist.mistchat.server.common.config.convert;

import com.google.common.collect.Maps;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

public class StringToEnumConverter<T extends BaseEnum> implements Converter<String, T> {
    private Map<String, T> enumMap = Maps.newHashMap();

    public StringToEnumConverter(Class<T> enumType) {
        for (T e : enumType.getEnumConstants()) {
            enumMap.put(e.getCode().toString(), e);
        }
    }

    @Override
    public T convert(String source) {
        T t = enumMap.get(source);
        if (t == null) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
