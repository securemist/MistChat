package cc.xmist.mistchat.server.common.interceptor;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class IpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIP = JakartaServletUtil.getClientIP(request);
        RequestContext.setIp(clientIP);
        return true;
    }
}
