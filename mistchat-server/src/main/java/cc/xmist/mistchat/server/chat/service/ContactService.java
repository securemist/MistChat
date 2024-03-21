package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.dao.MessageDao;
import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.common.enums.RoomType;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cc.xmist.mistchat.server.common.util.IdUtil;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final GroupMemberDao groupMemberDao;
    private final ContactDao contactDao;
    private final MessageDao messageDao;
    private final MessageService messageService;


    /**
     * 获取用户所有的会话列表
     *
     * @param uid
     * @return
     */
    public CursorResult<Contact> getContactList(Long uid, String cursor, Integer pageSize) {
        List<Contact> contacts = contactDao.listCursorable(uid, cursor, pageSize);
        Boolean isLast = false;
        String newCursor = null;
        if (contacts.size() != pageSize || CollectionUtil.isEmpty(contacts)) {
            isLast = true;
            cursor = null;
        } else {
            Contact last = CollectionUtil.getLast(contacts);
            newCursor = last.getId().toString();
        }

        contacts.forEach(c -> c.setUnreadCount(getUnreadCount(c)));

        return new CursorResult<Contact>(newCursor, isLast, contacts);
    }

    /**
     * 获取会话内未读消息数
     * @param contact
     * @return
     */
    private Long getUnreadCount(Contact contact) {
        Long unread = 0L;
        Long activeMsgId = contact.getActiveMsgId();
        Long readMsgId = contact.getReadMsgId();
        // readMsgId 为 null 说明加入群聊之后还从未读过消息，算作已读数为 0
        if (!(activeMsgId == null || readMsgId == null || activeMsgId.equals(readMsgId))) {
            Long lastMsgId = contactDao.getLastMsgId(contact.getRoomId());
            unread=  messageDao.calUnread(contact.getRoomId(), readMsgId, lastMsgId);
        }
        return unread;
    }


    /**
     * 更新会话
     *
     * @param uid
     * @param chatId
     * @param msgId
     */
    public void updateContact(Long uid, Contact contact, Long msgId) {
        contactDao.updateSending(contact.getId(), msgId);
        if(IdUtil.getRoomType(contact.getRoomId()) == RoomType.GROUP) {
            groupMemberDao.updateActive(uid, contact.getRoomId());
        }
    }

    // 用户读取消息
    public void readMsg(Long uid, Long roomId, Long msgId) {
        contactDao.updateReading(uid, roomId, msgId);
    }

}
