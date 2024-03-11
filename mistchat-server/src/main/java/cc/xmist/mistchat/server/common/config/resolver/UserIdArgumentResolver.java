package cc.xmist.mistchat.server.common.config.resolver;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.exception.NotLoginException;
import cc.xmist.mistchat.server.user.service.AuthService;
import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.Resource;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 解析 controller 层方法上的 uid
 */
@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    private static AuthService authService = SpringUtil.getBean(AuthService.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterName().equals("uid") && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return RequestContext.getUid();
    }
}
