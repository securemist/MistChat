package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.resp.ContactResponse;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final GroupMemberDao groupMemberDao;
    private final ContactDao contactDao;
    private final MessageService messageService;

    /**
     * 获取用户所有的会话列表
     *
     * @param uid
     * @return
     */
    public ContactResponse getContactList(Long uid) {
        List<Contact> contacts = contactDao.listByUid(uid);
        List<ContactResponse.Contact> list = contacts.stream().map(c -> {
            Integer unread = 0;
            if (!(c.getReadMsgId() == null || c.getLastMsgId() == null
                    || c.getLastMsgId().equals(c.getReadMsgId()))) {
            }
            messageService.getUnreadCount();
            return ContactResponse.Contact.builder()
                    .chatId(c.getChatId())
                    .chatType(c.getChatType())
                    .uid(c.getUid())
                    .id(c.getId())
                    .lastMsgId(c.getLastMsgId())
                    .readMsgId(c.getReadMsgId())
                    .build();
        }).collect(Collectors.toList());
        return ContactResponse.builder()
                .list(list)
                .build();
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
        if (contact.getChatType().equals(ChatType.GROUP)) {
            groupMemberDao.updateActive(uid, contact.getChatId());
        }
    }

    // 用户读取消息
    public void readMsg(Long uid, Long contactId, Long msgId) {
        contactDao.updateReading(uid, contactId, msgId);
    }
}
