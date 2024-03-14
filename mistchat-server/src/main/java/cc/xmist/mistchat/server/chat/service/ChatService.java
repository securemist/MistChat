package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.message.AbstractMsgHandler;
import cc.xmist.mistchat.server.chat.message.MessageHandleFactory;
import cc.xmist.mistchat.server.chat.model.ChatMessage;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.ChatType;
import cc.xmist.mistchat.server.chat.model.req.ChatMessageReq;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

    @Resource
    private ApplicationEventPublisher eventPublisher;


    public void sendMsg(Long uid, ChatMessageReq req) {
        ChatMessage message = req.getMessage();
        Long targetId = req.getTargetId();
        AbstractMsgHandler messageHandler = MessageHandleFactory.getHandle(message.getType());

        Message m = switch (req.getChatType()) {
            case FRIEND -> messageHandler.saveMsg(uid, ChatType.FRIEND, targetId, message);
            case GROUP -> messageHandler.saveMsg(uid, ChatType.GROUP, targetId, message);
        };
        eventPublisher.publishEvent(new MessageSendEvent(this, req.getChatType(), targetId, m));
    }
}
