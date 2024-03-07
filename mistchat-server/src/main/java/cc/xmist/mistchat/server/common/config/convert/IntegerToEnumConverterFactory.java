package cc.xmist.mistchat.server.common.config.convert;

import com.google.common.collect.Maps;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;

public class IntegerToEnumConverterFactory implements ConverterFactory<Integer, BaseEnum> {
    private static final Map<Class, Converter> CONVERTERS = Maps.newHashMap();

    @Override
    public <T extends BaseEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
        Converter converter = CONVERTERS.get(targetType);
        if(converter == null) {
            converter = new IntegerToEnumConverter(targetType);
            CONVERTERS.put(targetType,converter);
        }
        return converter;
    }
}
