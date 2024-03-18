package cc.xmist.mistchat.server.socketio.event;

import cc.xmist.mistchat.server.socketio.enums.SEvent;
import com.corundumstudio.socketio.SocketIOClient;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class AuthEvent extends BaseEvent {
    private Data data;

    public AuthEvent(Data data) {
        super(SEvent.AUTH);
        this.data = data;
    }

    @NoArgsConstructor
    @lombok.Data
    public static class Data {
        private boolean success;
        private String msg;
    }
}
