package cc.xmist.mistchat.server.common.interceptor;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.exception.BlackException;
import cc.xmist.mistchat.server.user.dao.BlackDao;
import cc.xmist.mistchat.server.user.model.entity.Black;
import cc.xmist.mistchat.server.user.service.BlackService;
import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class BlackInterceptor implements HandlerInterceptor {
    @Resource
    private BlackService blackService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = RequestContext.getUid();
        String ip = RequestContext.getIp();
        List<String> blockedIp = blackService.getBlockedIp();
        List<Long> blockedUid = blackService.getBlockedUid();


        if (blockedIp.contains(ip) || blockedUid.contains(uid)) {
            throw new BlackException();
        }

        return true;
    }
}
