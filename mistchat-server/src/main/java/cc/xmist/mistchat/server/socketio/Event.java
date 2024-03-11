package cc.xmist.mistchat.server.socketio;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Event {
    ONLINE,
    OFFLINE,
    CHAT
}
