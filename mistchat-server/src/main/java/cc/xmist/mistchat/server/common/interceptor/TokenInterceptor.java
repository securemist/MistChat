package cc.xmist.mistchat.server.common.interceptor;

import cc.xmist.mistchat.server.common.exception.NotLoginException;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.user.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * 登陆拦截器
 * 拦截获取token，判断用户是否登陆
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";

    @Resource
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        Long uid = authService.verify(token);
        // 用户未登陆并且是非公共接口，抛出异常
        if (uid == null && !isPublicURI(request)) {
            throw new NotLoginException();
        }

        RequestContext.setUid(uid);
        return true;
    }

    private boolean isPublicURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("public");
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        return Optional.ofNullable(authorization)
                .filter(h -> h.startsWith(AUTHORIZATION_PREFIX))
                .map(h -> h.replaceFirst(AUTHORIZATION_PREFIX, ""))
                .orElse(null);
    }
}
