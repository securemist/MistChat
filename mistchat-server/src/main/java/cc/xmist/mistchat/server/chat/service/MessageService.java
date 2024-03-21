package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.dao.MessageDao;
import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.message.AbstractMsgHandler;
import cc.xmist.mistchat.server.chat.message.MessageHandleFactory;
import cc.xmist.mistchat.server.chat.req.MessageRequest;
import cc.xmist.mistchat.server.chat.resp.MessageResponse;
import cc.xmist.mistchat.server.common.event.MessageSendEvent;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final ApplicationEventPublisher eventPublisher;
    private final MessageDao messageDao;
    private final ContactDao contactDao;

    // 发送消息
    public MessageResponse.Message send(Long uid, Long roomId, MessageRequest req) {
        AbstractMsgHandler messageHandler = MessageHandleFactory.getHandle(req.getType());

        Contact contact = contactDao.getByRoomId(uid, roomId);

        Message m = messageHandler.saveMsg(uid, contact.getRoomId(), req);
        eventPublisher.publishEvent(new MessageSendEvent(this, uid, contact, m));


        return MessageResponse.build(m);
    }

    // 某个会话的消息列表
    public CursorResult<MessageResponse.Message> listMessage(Long roomId, String cursor, Integer pageSize) {
        List<Message> data = messageDao.listCursorable(roomId, cursor, pageSize);
        Boolean isLast = false;
        String newCursor = null;
        if (data.size() != pageSize || CollectionUtil.isEmpty(data)) {
            isLast = true;
            cursor = null;
        } else {
            Message last = CollectionUtil.getLast(data);
            newCursor = last.getId().toString();
        }

        List<MessageResponse.Message> list = data.stream()
                .map(m -> MessageResponse.build(m))
                .collect(Collectors.toList());

        return new CursorResult(newCursor, isLast, list);
    }


}
