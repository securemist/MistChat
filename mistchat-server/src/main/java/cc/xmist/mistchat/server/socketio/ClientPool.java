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
    private Map<Integer, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    public void online(Integer uid, SocketIOClient client) {
        clientMap.put(uid, client);
    }

    public Integer offline(SocketIOClient client) {
        for (Map.Entry<Integer, SocketIOClient> entry : clientMap.entrySet()) {
            if (entry.getValue().equals(client)) {
                clientMap.remove(entry.getKey());
                return entry.getKey();
            }
        }
        // ignore
        return null;
    }

    public boolean isOnline(Integer uid) {
        return clientMap.containsKey(uid);
    }

    public SocketIOClient get(Integer uid) {
        return clientMap.get(uid);
    }

    public Set<Integer> getAllOnlineUsers() {
        return clientMap.keySet();
    }

    /**
     * 返回给定的用户中不在线的用户
     *
     * @param targetUsers
     * @return
     */
    public List<Integer> filterOffline(List<Integer> targetUsers) {
        List<Integer> onlineUsers = filterOnline(targetUsers);
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
    public List<Integer> filterOnline(List<Integer> targetUsers) {
        return targetUsers.stream()
                .filter(i -> clientMap.keySet().contains(i))
                .collect(Collectors.toList());
    }
}
