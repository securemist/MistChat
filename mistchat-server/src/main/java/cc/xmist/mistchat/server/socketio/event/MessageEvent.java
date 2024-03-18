package cc.xmist.mistchat.server.socketio.event;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.socketio.enums.SEvent;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class MessageEvent extends BaseEvent {
    private Data data;

    public MessageEvent(List<Long> uids, Data data) {
        super(SEvent.MESSAGE, uids);
        this.data = data;
    }

    @lombok.Data
    public static class Data {
        private Message message;
    }
}
