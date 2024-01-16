package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.constant.RedisKey;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.util.JsonUtil;
import cc.xmist.mistchat.server.common.util.RedisUtil;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.model.req.LoginReq;
import cc.xmist.mistchat.server.user.model.resp.WsResponseBuilder;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.UserService;
import cc.xmist.mistchat.server.user.model.resp.WSBaseResponse;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebSocketService {

    @Resource
    public AuthService authService;

    @Resource
    public UserService userService;

    @Resource
    public UserDao userDao;

    /**
     * 用户登陆请求
     *
     * @param channel
     * @param data    登陆请求数据
     * @see LoginReq
     */
    public void handleLoginReq(Channel channel, String data) {
        LoginReq loginReq = JsonUtil.toObj(data, LoginReq.class);
        WSBaseResponse wsResponse;
        try {
            User user = userService.login(loginReq.getUsername(), loginReq.getPassword());
            String token = RedisUtil.getStr(RedisKey.getKey(RedisKey.USER_TOKEN_STRING, user.getId()));

            wsResponse = WsResponseBuilder.loginSuccess(user, token);
            log.info("{} 登陆成功（账号登陆），token：{}", loginReq.getUsername(), token);
        } catch (BusinessException e) {
            wsResponse = WsResponseBuilder.failed(e.getMessage());
            log.info("{} 登陆失败：{}", loginReq.getUsername(), e.getMessage());
        }
        returnJsonData(channel, wsResponse);
    }


    /**
     * 认证请求
     * 只携带token的登陆请求
     *
     * @param channel
     * @param token
     */
    public void handleAuthorize(Channel channel, String token) {
        Long uid = authService.verify(token);
        WSBaseResponse wsResponse;
        // token失效
        if (uid == null) {
            wsResponse = WsResponseBuilder.invalidToken();
        } else {
            // token有效，续期token
            User user = userDao.getById(uid);
            authService.renewalToken(token);
            wsResponse = WsResponseBuilder.loginSuccess(user, token);
            log.info("{} 登陆成功（token登陆），token：{}", user.getName(), token);
        }
        returnJsonData(channel, wsResponse);
    }


    private void returnData(Channel channel, String data) {
        channel.writeAndFlush(new TextWebSocketFrame(data));
    }

    private void returnJsonData(Channel channel, Object json) {
        String res = JsonUtil.toJson(json);
        channel.writeAndFlush(new TextWebSocketFrame(res));
    }
}
