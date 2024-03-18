package cc.xmist.mistchat.server.socketio.event;

import cc.xmist.mistchat.server.common.enums.Role;
import cc.xmist.mistchat.server.socketio.enums.SEvent;
import lombok.Data;

import java.util.List;

public class OnlineEvent extends BaseEvent {
    private Data data;

    public OnlineEvent(List<Long> uids, Data data) {
        super(SEvent.ONLINE, uids);
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @lombok.Data
    public static class Data {
        private Long uid;
        private String name;
        private String avatar;
        private String gender;
        private Role role;
    }
}
