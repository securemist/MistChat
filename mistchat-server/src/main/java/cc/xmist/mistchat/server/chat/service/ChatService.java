package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.message.AbstractMessageHandler;
import cc.xmist.mistchat.server.chat.message.MessageAdapter;
import cc.xmist.mistchat.server.chat.message.MessageHandleFactory;
import cc.xmist.mistchat.server.chat.model.dao.RoomFriendDao;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.entity.Room;
import cc.xmist.mistchat.server.chat.model.req.FriendMessageReq;
import cc.xmist.mistchat.server.chat.model.ChatMessage;
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
    @Resource
    private RoomFriendDao roomFriendDao;


    /**
     * 发送好友消息
     *
     * @param uid
     * @param targetUid
     * @param message
     */
    public void setFriendMsg(Long uid, Long targetUid, ChatMessage message) {
        AbstractMessageHandler messageHandler = MessageHandleFactory.getHandle(message.getType());
        Long roomId = roomFriendDao.getId(uid, targetUid);
        Message m = messageHandler.saveMsg(uid, roomId, message);
        eventPublisher.publishEvent(new MessageSendEvent(this, message.getType(), m));
    }
}
