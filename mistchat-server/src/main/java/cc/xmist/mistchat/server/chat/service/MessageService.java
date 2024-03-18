package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.dao.GroupContactDao;
import cc.xmist.mistchat.server.chat.dao.MessageDao;
import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.message.AbstractMsgHandler;
import cc.xmist.mistchat.server.chat.message.MessageHandleFactory;
import cc.xmist.mistchat.server.chat.model.ChatMessage;
import cc.xmist.mistchat.server.chat.model.req.ChatMessageReq;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import cc.xmist.mistchat.server.common.util.Cursor;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final ApplicationEventPublisher eventPublisher;
    private final GroupContactDao groupContactDao;
    private final FriendContactDao friendContactDao;
    private final MessageDao messageDao;

    public void send(Long uid, ChatMessageReq req) {
        ChatMessage message = req.getMessage();
        Long targetId = req.getTargetId();
        AbstractMsgHandler messageHandler = MessageHandleFactory.getHandle(message.getType());

        Message m = messageHandler.saveMsg(uid, req.getChatType(), targetId, message);

        eventPublisher.publishEvent(new MessageSendEvent(this, req.getChatType(), targetId, m));
    }

    public CursorResult list(Long chatId, ChatType chatType, String cursor, Integer pageSize) {
        List<Message> data = messageDao.listCursorable(chatId, chatType, cursor, pageSize);

        Boolean isLast = false;
        String newCursor = null;
        if (data.size() != pageSize || CollectionUtil.isEmpty(data)) {
            isLast = true;
            cursor = null;
        } else {
            Message last = CollectionUtil.getLast(data);
            newCursor = last.getId().toString();
        }

        return new CursorResult(newCursor, isLast, data);
    }
}
