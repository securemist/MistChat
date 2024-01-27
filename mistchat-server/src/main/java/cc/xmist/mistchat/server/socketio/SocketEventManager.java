package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.UserService;
import com.corundumstudio.socketio.SocketIOClient;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketEventManager {

    @Resource
    ApplicationEventPublisher eventPublisher;

    //    private Map<UUID, Long> sessionMap = new ConcurrentHashMap<>();
    private Map<Long, SocketIOClient> clientMap = new ConcurrentHashMap<>();


    public void emitEvent(Long uid, String event, Object data) {
        SocketIOClient client = clientMap.get(uid);
        if (client == null) return;

        client.sendEvent(event, data);
    }

    protected final Long userDisconnect(SocketIOClient client) {
        Long uid = clientMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(client))
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
        clientMap.remove(uid);
        return uid;
    }

    protected final void userConnect(Long uid, SocketIOClient client) {
        clientMap.put(uid, client);
    }

    protected final Long getUid(SocketIOClient client) {
        for (var entry : clientMap.entrySet()) {
            if (entry.getValue().equals(client)) {
                return entry.getKey();
            }
        }
        return null;
    }

}
