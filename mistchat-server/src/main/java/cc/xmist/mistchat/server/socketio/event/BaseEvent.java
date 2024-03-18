package cc.xmist.mistchat.server.socketio.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public abstract class BaseEvent {
    private String name;

    public abstract Object getData();
}
