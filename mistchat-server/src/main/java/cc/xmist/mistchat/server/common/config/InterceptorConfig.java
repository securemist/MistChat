package cc.xmist.mistchat.server.common.config;


import cc.xmist.mistchat.server.common.interceptor.BlackInterceptor;
import cc.xmist.mistchat.server.common.interceptor.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    BlackInterceptor blackInterceptor;
    @Resource
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor);
//        registry.addInterceptor(blackInterceptor);
    }
}
