package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.socketio.event.BaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventEmitter {

    private final ClientPool clientPool;

    private void emit(Long uid, String name, Object data) {
        if (clientPool.isOnline(uid)) {
            clientPool.get(uid).sendEvent(name, data);
        }
    }

    public void emit(Long uid, BaseEvent event) {
        emit(uid, event.getName(), event.getData());
    }

    public void emits(List<Long> uids, BaseEvent event) {
        uids.forEach(uid -> {
            emit(uid, event.getName(), event.getData());
        });
    }
}
