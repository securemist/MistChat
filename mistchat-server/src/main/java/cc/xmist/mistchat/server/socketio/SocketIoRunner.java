package cc.xmist.mistchat.server.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
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

    @Value("${ws.enabled}")
    private Boolean enabled;

    @Override
    public void run(String... args) throws Exception {
        if (enabled) {
            socketIOServer.start();
        }
    }

    @PreDestroy
    public void shutdown() {
        if (enabled) {
            log.debug("shutdown socketIo Server");
            socketIOServer.stop();
        }
    }
}
