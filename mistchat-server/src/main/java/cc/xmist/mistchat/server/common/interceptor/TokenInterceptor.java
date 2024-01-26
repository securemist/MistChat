package cc.xmist.mistchat.server.common.interceptor;

import cc.xmist.mistchat.server.common.exception.NotLoginException;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.user.service.AuthService;
import io.netty.util.NetUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;

/**
 * 登陆拦截器
 * 拦截获取token，判断用户是否登陆
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";

    @Value("${springdoc.api-docs.path}")
    private String docPath;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Resource
    private AuthService authService;

    private String[] openApiPaths = new String[]{"webjars", "doc.html", "swagger", "favicon.ico", "/v3/api-docs"};

    @PostConstruct
    public void log() {
        log.info("项目接口文档：{} ", contextPath + "doc.html");
        log.info("项目接口地址：{} ", contextPath + "/v3/api-docs");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isDocURI(request)) {
            return true;
        }

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

    private boolean isDocURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String path : openApiPaths) {
            if (uri.contains(path)) {
                return true;
            }
        }
        return false;
    }
}
