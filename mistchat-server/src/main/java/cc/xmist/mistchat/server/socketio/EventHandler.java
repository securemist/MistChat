package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.common.event.UserOfflineEvent;
import cc.xmist.mistchat.server.common.event.UserOnlineEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.socketio.enums.REvent;
import cc.xmist.mistchat.server.socketio.enums.SEvent;
import cc.xmist.mistchat.server.socketio.enums.SocketResponseType;
import cc.xmist.mistchat.server.socketio.event.AuthEvent;
import cc.xmist.mistchat.server.socketio.model.LoginRequest;
import cc.xmist.mistchat.server.socketio.model.SocketResponse;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.UserService;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventHandler {
    private final AuthService authService;
    private final UserService userService;
    private final ClientPool clientPool;
    private final EventEmitter eventEmitter;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 建立连接时获取token并验证
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        Long uid = authService.verify(token);

        AuthEvent.Data data = new AuthEvent.Data();
        if (uid == null) {
            data.setSuccess(false);
            data.setMsg("账号或密码错误");
        } else {
            data.setSuccess(true);
            online(client, uid);
        }
        eventEmitter.emit(uid, new AuthEvent(data));
    }


    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        Long uid = clientPool.offline(client);
        log.info("{} 下线", uid);
        eventPublisher.publishEvent(new UserOfflineEvent(this, uid));
    }

    @OnEvent(value = REvent.LOGIN)
    public void onLoginEvent(SocketIOClient client, AckRequest request, LoginRequest loginRequest) {
        AuthEvent.Data data = new AuthEvent.Data();
        try {
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            data.setSuccess(true);
            online(client, user.getId());
        } catch (BusinessException e) {
            data.setSuccess(false);
            data.setMsg(e.getMessage());
        }
        client.sendEvent(SEvent.AUTH, data);
    }


    private void online(SocketIOClient client, Long uid) {
        clientPool.online(uid, client);
        String ip = ((InetSocketAddress) client.getRemoteAddress()).getHostString();
        eventPublisher.publishEvent(new UserOnlineEvent(this, uid, ip));
        log.info("{} 上线，ip: {}", uid, ip);
    }
}
