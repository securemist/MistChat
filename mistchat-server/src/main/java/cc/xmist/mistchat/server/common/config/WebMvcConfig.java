package cc.xmist.mistchat.server.common.config;

import cc.xmist.mistchat.server.common.config.convert.IntegerToEnumConverterFactory;
import cc.xmist.mistchat.server.common.config.convert.StringCodeToEnumConverterFactory;
import cc.xmist.mistchat.server.common.config.resolver.UserIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringCodeToEnumConverterFactory());
        registry.addConverterFactory(new IntegerToEnumConverterFactory());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserIdArgumentResolver());
    }
}
