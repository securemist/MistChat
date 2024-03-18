package cc.xmist.mistchat.server.socketio.enums;

import org.apache.http.auth.AUTH;

public interface SEvent {
    String MESSAGE = "MESSAGE";
    String ONLINE = "ONLINE";
    String OFFLINE = "OFFLINE";
    String AUTH = "AUTH";
}
