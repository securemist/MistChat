package cc.xmist.mistchat.server.common.config;

import cc.xmist.mistchat.server.common.config.convert.IntegerToEnumConverterFactory;
import cc.xmist.mistchat.server.common.config.convert.StringCodeToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringCodeToEnumConverterFactory());
        registry.addConverterFactory(new IntegerToEnumConverterFactory());
    }
}
