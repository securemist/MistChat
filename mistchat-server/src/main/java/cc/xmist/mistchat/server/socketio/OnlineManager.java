package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.common.event.UserOfflineEvent;
import cc.xmist.mistchat.server.common.event.UserOnlineEvent;
import cc.xmist.mistchat.server.socketio.model.SEvent;
import com.corundumstudio.socketio.SocketIOClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class OnlineManager {
    @Resource
    ApplicationEventPublisher eventPublisher;
    private Map<Long, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    public void online(Long uid, SocketIOClient client) {
        clientMap.put(uid, client);
        log.info("{} 上线", uid);

        InetSocketAddress remoteAddress = ((InetSocketAddress) client.getRemoteAddress());
        String ip = remoteAddress.getHostString();
        eventPublisher.publishEvent(new UserOnlineEvent(this, uid, ip));

        emitEvent(SEvent.ONLINE, uid);
    }

    public Long offline(SocketIOClient client) {
        Long uid = clientMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(client))
                .map(Map.Entry::getKey)
                .findFirst()
                .get();
        log.info("{} 下线", uid);
        eventPublisher.publishEvent(new UserOfflineEvent(this, uid));
        emitEvent(SEvent.OFFLINE, uid);
        return uid;
    }

    public void getOnlineUsersId() {
        return;
    }

    public boolean isOnline(Long uid) {
        return clientMap.keySet().contains(uid);
    }


    public void emitIfOnline(Long uid, SEvent event, Object data) {
        if (isOnline(uid)) {
            emitEvent(uid, event, data);
        }
    }

    public void emitIfOnline(List<Long> uidList, SEvent event, Object data) {
        for (Long uid : uidList) {
            if (isOnline(uid)) {
                emitEvent(uid, event, data);
            }
        }
    }

    public void emitEvent(Long uid, SEvent event, Object data) {
        SocketIOClient client = clientMap.get(uid);
        client.sendEvent(event.name(), data);
    }

    public void emitEvent(List<Long> uidList, SEvent event, Object data) {
        for (Long uid : uidList) {
            SocketIOClient client = clientMap.get(uid);
            emitEvent(uid, event, data);
        }
    }

    public void emitEvent(SEvent event, Object data) {
        for (Long uid : clientMap.keySet()) {
            SocketIOClient client = clientMap.get(uid);
            emitEvent(uid, event, data);
        }
    }
}
