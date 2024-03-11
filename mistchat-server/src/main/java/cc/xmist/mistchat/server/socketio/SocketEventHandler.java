package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.common.event.UserOfflineEvent;
import cc.xmist.mistchat.server.common.event.UserOnlineEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.socketio.model.*;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.UserService;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.UUID;

@Component
@Slf4j
public class SocketEventHandler {
    @Resource
    private AuthService authService;
    @Resource
    private UserService userService;
    @Resource
    private OnlineManager onlineManager;
    @Resource
    private ApplicationEventPublisher eventPublisher;

    /**
     * 建立连接时获取token并验证
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        UUID sessionId = client.getSessionId();
        Long uid = authService.verify(token);

        boolean loginSuccess = uid != null;
        if (loginSuccess) { // 验签成功，登陆
            onlineManager.online(uid, client);
        }
        client.sendEvent(SEvent.AUTH.name(), loginSuccess);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        // 用户下线
        Long uid = onlineManager.offline(client);
    }

    /**
     * 专门的登陆请求
     */
    @OnEvent(value = "login")
    public void onLoginEvent(SocketIOClient client, AckRequest request, LoginRequest loginRequest) {
        User user = null;

        try {
            user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (BusinessException e) {
            request.sendAckData(SocketResponse.build(SocketResponseType.LOGIN_FAILED, null));
            return;
        }

        // 签发token
        Long uid = user.getId();
        String token = authService.login(uid);
        boolean loginSuccess = uid != null;
        if (!loginSuccess) { // 验签成功，登陆
            request.sendAckData(SocketResponse.build(SocketResponseType.LOGIN_FAILED));
        }

        onlineManager.online(uid, client);
        request.sendAckData(SocketResponse.build(SocketResponseType.LOGIN_SUCCESS, token));
    }

}
