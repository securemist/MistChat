package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.socketio.model.SEvent;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SocketService {

    @Resource
    private SocketEventPublisher socketEventPublisher;
    @Resource
    private OnlineManager onlineManager;


    public void userOnline(Long uid) {
        onlineManager.emitEvent(SEvent.ONLINE, uid);
    }

    public void sendToUser(Long uid, Message message) {
        onlineManager.emitIfOnline(uid, SEvent.MESSAGE, message);
    }

    public void sendToGroup(List<Long> uidList, Message message) {
        onlineManager.emitIfOnline(uidList, SEvent.MESSAGE, message);
    }


}
