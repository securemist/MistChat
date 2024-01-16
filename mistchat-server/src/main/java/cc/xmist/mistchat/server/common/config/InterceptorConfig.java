package cc.xmist.mistchat.server.common.config;


import cc.xmist.mistchat.server.common.interceptor.IpInterceptor;
import cc.xmist.mistchat.server.common.interceptor.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    @Resource
    private IpInterceptor ipInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor);
        registry.addInterceptor(ipInterceptor);
    }
}
