package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.message.AbstractMessageHandler;
import cc.xmist.mistchat.server.chat.message.MessageHandleFactory;
import cc.xmist.mistchat.server.chat.model.req.ChatMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

    public void sendMsg(Long fromUid, ChatMessageRequest request) {
        AbstractMessageHandler messageHandler = MessageHandleFactory.getHandle(request.getType());
        messageHandler.saveMsg(fromUid, request);
    }
}
