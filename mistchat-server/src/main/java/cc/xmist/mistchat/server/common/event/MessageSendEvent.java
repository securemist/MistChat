package cc.xmist.mistchat.server.common.event;

import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cc.xmist.mistchat.server.chat.service.ChatService;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class MessageSendEvent extends ApplicationEvent {
    private List<Long> targetUidList;
    private ChatMessageResponse messageResponse;

    public MessageSendEvent(Object source, List<Long> targetUidList, ChatMessageResponse messageResponse) {
        super(source);
        this.targetUidList = targetUidList;
        this.messageResponse = messageResponse;
    }

}
