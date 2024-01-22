package cc.xmist.mistchat.server.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketIoRunner implements CommandLineRunner {
    @Resource
    private SocketIOServer socketIOServer;

    @Value("${ws.port}")
    private String port;

    @Override
    public void run(String... args) throws Exception {
        socketIOServer.start();
        log.info("websocket 服务启动成功，端口：{}", port);
    }
}
