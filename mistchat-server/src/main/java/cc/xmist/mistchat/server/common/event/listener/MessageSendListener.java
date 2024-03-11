package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.ChatType;
import cc.xmist.mistchat.server.chat.service.RoomService;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import cc.xmist.mistchat.server.socketio.SocketService;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageSendListener {
    @Resource
    private SocketService socketService;
    @Resource
    private RoomService roomService;

    @EventListener(MessageSendEvent.class)
    public void send(MessageSendEvent event) {
        Message message = event.getMessage();
        List<Long> targetIds = new ArrayList();
        Long uid = message.getUid();

        if (event.getType() == ChatType.FRIEND) {
            targetIds = Arrays.asList(event.getTargetId());
        } else {
            // 获取群所有成员
        }

        switch (event.getType()) {
            case FRIEND: {
                socketService.sendToUser(targetIds.get(0), message);
            }
            ;
        }
    }
}
