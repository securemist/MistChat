package cc.xmist.mistchat.server.chat.message.handler;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.entity.MessageExtra;
import cc.xmist.mistchat.server.chat.message.AbstractMsgHandler;
import cc.xmist.mistchat.server.chat.message.extra.SystemMsgExtra;
import cc.xmist.mistchat.server.common.enums.MessageType;

public class SystemMsgHandle extends AbstractMsgHandler<SystemMsgExtra> {
    @Override
    protected MessageType getMsgType() {
        return null;
    }

    @Override
    protected Message customSaveMsg(Message message, SystemMsgExtra extra) {
        MessageExtra messageExtra = MessageExtra.builder().sysMsgExtra(extra).build();
        message.setExtra(messageExtra);
        return message;
    }

    @Override
    public void recall() {

    }
}
