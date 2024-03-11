package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.socketio.model.SEvent;
import cc.xmist.mistchat.server.socketio.model.WsMessageVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SocketService {

    @Resource
    private SocketEventPublisher socketEventPublisher;
    @Resource
    private OnlineManager onlineManager;

//    public void sendToUser(List<Long> uidList, ChatMessageResponse message) {
//        uidList.forEach(uid -> {
//            sendToUser(uid, message);
//        });
//    }

    public void userOnline(Long uid) {
        onlineManager.emitEvent(SEvent.ONLINE, uid);
    }

    public void sendToUser(Long uid, ChatMessageResponse message) {
        socketEventPublisher.sendMsg(uid, message);
    }

    public void sendToGroup() {

    }
}
