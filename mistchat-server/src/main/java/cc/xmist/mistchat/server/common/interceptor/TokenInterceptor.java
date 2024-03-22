package cc.xmist.mistchat.server.common.interceptor;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.user.service.AuthService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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

    @PostConstruct
    public void log() {
        log.info("项目接口文档：{} ", contextPath + "doc.html");
        log.info("项目接口地址：{} ", contextPath + "/v3/api-docs");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!isPublicURI(request)) {
            String token = request.getHeader("token");
            Integer uid = authService.verify(token);
            RequestContext.setUid(uid);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContext.remove();
    }

    private boolean isPublicURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("public");
    }
}
