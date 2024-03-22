package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.dao.MessageDao;
import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.resp.ContactResponse;
import cc.xmist.mistchat.server.common.config.GroupConfig;
import cc.xmist.mistchat.server.common.enums.RoomType;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final GroupMemberDao groupMemberDao;
    private final ContactDao contactDao;
    private final GroupConfig groupConfig;
    private final MessageDao messageDao;
    private final MessageService messageService;


    /**
     * 获取用户所有的会话列表
     *
     * @param uid
     * @return
     */
    public CursorResult<ContactResponse.Contact> getContactList(Integer uid, String cursor, Integer pageSize) {
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

        List<ContactResponse.Contact> list = contacts.stream()
                .map(c -> {
                    c.setUnreadCount(getUnreadCount(c));
                    return ContactResponse.build(c);
                })
                .collect(Collectors.toList());

        return new CursorResult<ContactResponse.Contact>(newCursor, isLast, list);
    }

    /**
     * 获取单个会话信息，通常用于成为好友之后
     *
     * @param roomId
     * @return
     */
    public Contact getContact(Integer uid, String  roomId) {
        Contact contact = contactDao.getByRoomId(uid, roomId);
        Integer unread = getUnreadCount(contact);
        contact.setUnreadCount(unread);
        return contact;
    }

    /**
     * 获取会话内未读消息数
     *
     * @param contact
     * @return
     */
    private Integer getUnreadCount(Contact contact) {
        Integer unread = 0;
        Integer activeMsgId = contact.getActiveMsgId();
        Integer readMsgId = contact.getReadMsgId();
        // readMsgId 为 null 说明加入群聊之后还从未读过消息，算作已读数为 0
        if (!(activeMsgId == null || readMsgId == null || activeMsgId.equals(readMsgId))) {
            Integer lastMsgId = contactDao.getLastMsgId(contact.getRoomId());
            unread = messageDao.calUnread(contact.getRoomId(), readMsgId, lastMsgId);
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
    public void updateContact(Integer uid, Contact contact, Integer msgId) {
        contactDao.updateSending(contact.getId(), msgId);
        if (groupConfig.getRoomType(contact.getRoomId()) == RoomType.GROUP) {
            groupMemberDao.updateActive(uid, contact.getRoomId());
        }
    }

    // 用户读取消息
    public void readMsg(Integer uid, Integer roomId, Integer msgId) {
        contactDao.updateReading(uid, roomId, msgId);
    }

}
