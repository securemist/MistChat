package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.dao.MessageDao;
import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.resp.ContactResponse;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.enums.SystemMsgType;
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
    private final MessageDao messageDao;
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

    /**
     * 两人成为好友后初始化会话信息
     *
     * @param uid1
     * @param uid2
     */
    public void initFriend(Long uid1, Long uid2) {
        List<Long> contactIds = contactDao.initFriendContact(uid1, uid2);

        List<Long> msgIds = messageDao.initSystemMsg(contactIds, SystemMsgType.BE_FRIEND);

        contactDao.initLastMsgId(contactIds, msgIds);
    }

    /**
     * 创建群聊初始化会话信息
     * TODO 里面有 for 循环套 update，需要限制群聊人数
     *
     * @param groupId
     * @param uids
     */
    public void initGroup(Long groupId, List<Long> uids) {
        List<Long> contactIds = contactDao.initGroupContacts(groupId, uids);

        List<Long> msgIds = messageDao.initGroupMsg(contactIds);

        contactDao.initLastMsgId(contactIds, msgIds);
    }
}
