package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.mapper.ContactMapper;
import cc.xmist.mistchat.server.common.enums.ChatType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {

    /**
     * 用户发送消息的会话更新
     *
     * @param uid
     * @param chatType
     * @param chatId
     * @param msgId
     */
    public void updateSending(Long uid, ChatType chatType, Long chatId, Long msgId) {
        lambdaUpdate()
                .set(Contact::getLastMsgId, msgId)
                .eq(Contact::getUid, uid)
                .eq(Contact::getChatType, chatType)
                .eq(Contact::getChatId, chatId);
    }

    /**
     * 用户读取消息的会话更新
     *
     * @param uid
     * @param chatType
     * @param chatId
     * @param msgId
     */
    public void updateReading(Long uid, ChatType chatType, Long chatId, Long msgId) {
        lambdaUpdate()
                .set(Contact::getReadMsgId, msgId)
                .eq(Contact::getUid, uid)
                .eq(Contact::getChatType, chatType)
                .eq(Contact::getChatId, chatId);
    }

    /**
     * 两个用户成为好友，创建会话
     *
     * @param uid1
     * @param uid2
     */
    public void initOnFriend(Long uid1, Long uid2) {
        Contact c1 = Contact.builder()
                .uid(uid1)
                .chatId(uid2)
                .chatType(ChatType.FRIEND)
                .build();

        Contact c2 = Contact.builder()
                .uid(uid2)
                .chatId(uid1)
                .chatType(ChatType.FRIEND)
                .build();

        saveBatch(Arrays.asList(c1, c2));
    }

    /**
     * 群聊首次创建时，初始化会话
     * @param groupId
     * @param uids
     */
    public void initOnGroup(Long groupId, List<Long> uids) {
        List<Contact> contacts = uids.stream().map(uid -> {
            return Contact.builder()
                    .uid(uid)
                    .chatId(groupId)
                    .chatType(ChatType.GROUP)
                    .build();
        }).collect(Collectors.toList());

        saveBatch(contacts);
    }
}
