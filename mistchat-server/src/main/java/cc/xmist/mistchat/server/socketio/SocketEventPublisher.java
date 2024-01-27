package cc.xmist.mistchat.server.socketio;

import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.socketio.model.SEvent;
import cc.xmist.mistchat.server.socketio.model.WsMessageVo;
import com.corundumstudio.socketio.SocketIOClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class SocketEventPublisher {
    @Resource
    private SocketEventManager eventManager;

    public void sendMsg(Long targetUid, ChatMessageResponse messageResponse) {
        eventManager.emitEvent(targetUid, SEvent.MESSAGE, messageResponse);
        // 根据uid拿到对应的sessionId
    }

}
