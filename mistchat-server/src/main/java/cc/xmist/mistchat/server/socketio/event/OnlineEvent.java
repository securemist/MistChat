package cc.xmist.mistchat.server.socketio.event;

import cc.xmist.mistchat.server.common.enums.Role;
import cc.xmist.mistchat.server.socketio.enums.SEvent;
import cc.xmist.mistchat.server.user.model.resp.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class OnlineEvent extends BaseEvent {
    private Data data;

    public OnlineEvent(Long uid) {
        super(SEvent.ONLINE);
        this.data = new Data(uid);
    }

    @Override
    public Object getData() {
        return data;
    }

    @lombok.Data
    @AllArgsConstructor
    public static class Data {
        private Long uid;
    }
}
