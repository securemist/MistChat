package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.message.AbstractMessageHandler;
import cc.xmist.mistchat.server.chat.message.MessageAdapter;
import cc.xmist.mistchat.server.chat.message.MessageHandleFactory;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.entity.Room;
import cc.xmist.mistchat.server.chat.model.req.ChatMessageRequest;
import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatService {

    @Resource
    private ApplicationEventPublisher eventPublisher;
    @Resource
    private RoomService roomService;

    public void sendMsg(Long fromUid, ChatMessageRequest request) {
        AbstractMessageHandler messageHandler = MessageHandleFactory.getHandle(request.getType());
        Message message = messageHandler.saveMsg(fromUid, request);
        Room room = roomService.getById(request.getRoomId());

        List<Long> roomUsers = roomService.getRoomUsers(request.getRoomId());

        List<Long> targetUidList =
                switch (room.getType()) {
                    case FRIEND -> roomUsers.stream()
                            .filter(uid -> !uid.equals(fromUid))
                            .collect(Collectors.toList());
                    case GROUP -> roomUsers;
                    default -> roomUsers;
                };

        ChatMessageResponse messageResponse = MessageAdapter.buildResponse(fromUid, message);
        eventPublisher.publishEvent(new MessageSendEvent(this, targetUidList, messageResponse));
    }
}
