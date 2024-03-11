package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.GroupContact;
import cc.xmist.mistchat.server.chat.model.enums.ChatType;
import cc.xmist.mistchat.server.chat.model.mapper.GroupContactMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class GroupContactDao extends ServiceImpl<GroupContactMapper, GroupContact> {

//    public void friendContactCreate(Long uid, Long friendId) {
//        Contact c1 = Contact.builder()
//                .chatType(ChatType.FRIEND)
//                .chatId(friendId)
//                .uid(uid)
//                .build();
//
//        Contact c2 = Contact.builder()
//                .chatType(ChatType.FRIEND)
//                .chatId(friendId)
//                .uid(uid)
//                .build();
//        saveBatch(Arrays.asList(c2, c1));
//    }

//    public void groupContactCreate(Long uid, Long groupId) {
//        Contact contact = Contact.builder()
//                .chatType(ChatType.GROUP)
//                .chatId(groupId)
//                .uid(uid)
//                .build();
//        save(contact);
//    }
//
//    public List<Contact> list(Long uid) {
//        return lambdaQuery()
//                .eq(Contact::getUid, uid)
//                .orderByAsc(Contact::getLastMsgId)
//                .list();
//    }
//
//    // 发送消息更新会话
//    public void updateBySend(Long uid, ChatType chatType, Long chatId, Long messageId) {
//        lambdaUpdate()
//                .eq(Contact::getUid, uid)
//                .eq(Contact::getChatType, chatType)
//                .eq(Contact::getChatId, chatType)
//                .set(chatType == ChatType.GROUP, Contact::getActiveTime, LocalDateTime.now())
//                .set(Contact::getLastMsgId, LocalDateTime.now())
//                .update();
//
//        lambdaUpdate()
//                .eq(Contact::getChatId, chatId)
//                .set(Contact::getLastMsgId, messageId)
//                .update();
//    }
//
//    // 阅读消息更新会话
//    public void updateByRead(Long contactId, Long messageId) {
//        lambdaUpdate()
//                .eq(Contact::getId, contactId)
//                .eq(Contact::getReadMsgId, messageId)
//                .update();
//    }
}
