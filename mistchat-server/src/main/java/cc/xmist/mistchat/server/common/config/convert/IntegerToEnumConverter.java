package cc.xmist.mistchat.server.common.config.convert;

import com.google.common.collect.Maps;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;
import java.util.Objects;

public class IntegerToEnumConverter<T extends BaseEnum> implements Converter<Integer, T> {
    private Map<Integer, T> enumMap = Maps.newHashMap();

    public IntegerToEnumConverter(Class<T> enumType) {
        for (T e : enumType.getEnumConstants()) {
            enumMap.put(e.getCode(), e);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (Objects.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
