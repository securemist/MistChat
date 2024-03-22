package cc.xmist.mistchat.server.socketio.event;

import cc.xmist.mistchat.server.common.enums.ActiveStatus;
import cc.xmist.mistchat.server.socketio.enums.SEvent;
import lombok.AllArgsConstructor;

public class ActiveEvent extends BaseEvent {
    private Data data;

    public ActiveEvent(Integer uid, ActiveStatus status) {
        super(SEvent.ACTIVE);
        this.data = new Data(uid, status);
    }

    @Override
    public Object getData() {
        return data;
    }

    @lombok.Data
    @AllArgsConstructor
    public static class Data {
        private Integer uid;
        private ActiveStatus status;
    }
}
