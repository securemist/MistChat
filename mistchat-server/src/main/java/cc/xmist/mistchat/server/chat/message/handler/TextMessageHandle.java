package cc.xmist.mistchat.server.chat.message.handler;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.entity.MessageExtra;
import cc.xmist.mistchat.server.chat.message.AbstractMsgHandler;
import cc.xmist.mistchat.server.chat.message.req.TextMessageRequest;
import cc.xmist.mistchat.server.common.enums.MessageType;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandle extends AbstractMsgHandler<TextMessageRequest> {
    @Override
    protected MessageType getMsgType() {
        return MessageType.TEXT;
    }

    @Override
    protected Message customSaveMsg(Message message, TextMessageRequest data) {
        MessageExtra extra = MessageExtra.builder()
                .content(data.getContent())
                .build();
        message.setExtra(extra);
        messageDao.save(message);
        return message;
    }

    @Override
    public void recall() {

    }
}
