package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.socketio.model.SEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketEventPublisher {
    @Resource
    private OnlineManager onlineManager;

    public void sendMsg(Long targetUid, ChatMessageResponse messageResponse) {
        onlineManager.emitEvent(targetUid, SEvent.MESSAGE, messageResponse);
        // 根据uid拿到对应的sessionId
    }

}
