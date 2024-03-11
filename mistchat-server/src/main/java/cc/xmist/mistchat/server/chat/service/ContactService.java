package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.model.enums.ChatType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    public void updateFriendContact(Long uid, Long chatId, Long messageId) {
//        contactDao.firstCreate(uid, ChatType.FRIEND, chatId, messageId);
    }
}
