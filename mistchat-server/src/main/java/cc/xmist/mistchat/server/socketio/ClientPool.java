package cc.xmist.mistchat.server.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientPool {
    private Map<Long, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    public void online(Long uid, SocketIOClient client) {
        clientMap.put(uid, client);
    }

    public Long offline(SocketIOClient client) {
        for (Map.Entry<Long, SocketIOClient> entry : clientMap.entrySet()) {
            if (entry.getValue().equals(client)) {
                clientMap.remove(entry.getKey());
                return entry.getKey();
            }
        }
        // ignore
        return null;
    }

    public boolean isOnline(Long uid) {
        return clientMap.containsKey(uid);
    }

    public SocketIOClient get(Long uid) {
        return clientMap.get(uid);
    }

    public Set<Long> getAllOnlineUsers() {
        return clientMap.keySet();
    }

    /**
     * 返回给定的用户中不在线的用户
     *
     * @param targetUsers
     * @return
     */
    public List<Long> filterOffline(List<Long> targetUsers) {
        List<Long> onlineUsers = filterOnline(targetUsers);
        return targetUsers.stream()
                .filter(i -> !onlineUsers.contains(i))
                .collect(Collectors.toList());
    }

    /**
     * 返回给定的用户中在线的用户
     *
     * @param targetUsers
     * @return
     */
    public List<Long> filterOnline(List<Long> targetUsers) {
        return targetUsers.stream()
                .filter(i -> clientMap.keySet().contains(i))
                .collect(Collectors.toList());
    }
}
