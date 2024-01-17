package cc.xmist.mistchat.server.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketEventHandler {

    @OnConnect
    public void onConnect(SocketIOClient client) {
        log.info("connect");
    }

    @OnEvent(value = "join")
    public void onJoinEvent(SocketIOClient client, AckRequest request) {
    }
}
