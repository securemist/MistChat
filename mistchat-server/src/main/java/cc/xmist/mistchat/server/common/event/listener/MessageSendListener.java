package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.chat.message.MessageAdapter;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.MessageType;
import cc.xmist.mistchat.server.chat.model.enums.RoomType;
import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.chat.service.RoomService;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import cc.xmist.mistchat.server.socketio.SocketService;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageSendListener {
    @Resource
    private SocketService socketService;
    @Resource
    private RoomService roomService;

    @EventListener(MessageSendEvent.class)
    public void send(MessageSendEvent event) {
        ChatMessageResponse messageResponse = event.getMessageResponse();
        List<Long> targetUidList = event.getTargetUidList();
        Long roomId = messageResponse.getMessage().getRoomId();

        RoomType roomType = roomService.getRoomType(roomId);
        switch (roomType) {
            case FRIEND: {
                socketService.sendToUser(targetUidList.get(0), messageResponse);
            }
            ;
        }
    }


    public void getRoomType(Long roomId) {

    }

}
