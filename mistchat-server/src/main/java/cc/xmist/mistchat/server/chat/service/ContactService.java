package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.ContactDao;
import cc.xmist.mistchat.server.chat.resp.ContactResponse;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        return ContactResponse.builder()
                .build();
    }

    /**
     * 更新会话
     *
     * @param uid
     * @param chatType
     * @param chatId
     * @param msgId
     */
    public void updateContact(Long uid, ChatType chatType, Long chatId, Long msgId) {
        contactDao.updateSending(uid, chatType, chatId, msgId);
        if (chatType.equals(ChatType.GROUP)) {
            groupMemberDao.updateActive(uid, chatId);
        }
    }

    // 用户读取消息
    public void readMsg(Long uid, ChatType chatType, Long chatId, Long msgId) {
        contactDao.updateReading(uid,chatType,chatId,msgId);
//        switch (chatType) {
//            case FRIEND -> friendContactDao.readMsg(uid, chatId, msgId);
//            case GROUP -> groupContactDao.readMsg(uid, chatId, msgId);
//        }
    }
}
