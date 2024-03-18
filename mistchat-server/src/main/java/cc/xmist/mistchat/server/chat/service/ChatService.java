package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.message.AbstractMsgHandler;
import cc.xmist.mistchat.server.chat.message.MessageHandleFactory;
import cc.xmist.mistchat.server.chat.model.ChatMessage;
import cc.xmist.mistchat.server.chat.model.req.ChatMessageReq;
import cc.xmist.mistchat.server.common.enums.ChatType;
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

        Message m = messageHandler.saveMsg(uid, req.getChatType(), targetId, message);

        eventPublisher.publishEvent(new MessageSendEvent(this, req.getChatType(), targetId, m));
    }
}
