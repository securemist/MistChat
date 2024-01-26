package cc.xmist.mistchat.server.chat.message.handler;

import cc.xmist.mistchat.server.chat.message.AbstractMessageHandler;
import cc.xmist.mistchat.server.chat.message.req.TextMessageRequest;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.MessageType;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandle extends AbstractMessageHandler<TextMessageRequest> {
    @Override
    protected MessageType getMsgType() {
        return MessageType.TEXT;
    }

    @Override
    protected void customSaveMsg(Message message, TextMessageRequest data) {
        message.setContent(data.getContent());
        messageDao.save(message);
    }

    @Override
    public void recall() {

    }
}
