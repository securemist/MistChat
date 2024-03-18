package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.dao.GroupContactDao;
import cc.xmist.mistchat.server.chat.entity.FriendContact;
import cc.xmist.mistchat.server.chat.entity.GroupContact;
import cc.xmist.mistchat.server.chat.model.resp.ContactListResp;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final FriendContactDao friendContactDao;
    private final GroupContactDao groupContactDao;
    private final GroupMemberDao groupMemberDao;


    /**
     * 获取用户所有的会话列表
     *
     * @param uid
     * @return
     */
    public ContactListResp getContactList(Long uid) {
        List<FriendContact> friendContacts = friendContactDao.getByUid(uid);

        List<Long> groupsId = groupMemberDao.getBelongingGroupsId(uid);
        List<GroupContact> groupContacts = groupContactDao.listByIds(groupsId);

        return ContactListResp.builder()
                .friendContacts(friendContacts)
                .groupContacts(groupContacts)
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
        switch (chatType) {
            case FRIEND -> friendContactDao.updateActive(uid, chatId, msgId);
            case GROUP -> {
                groupMemberDao.updateActive(uid, chatId);
                groupContactDao.updateActive(uid, chatId, msgId);
            }
        }
    }

    // 用户读取消息
    public void readMsg(Long uid, ChatType chatType, Long chatId, Long msgId) {
        switch (chatType) {
            case FRIEND -> friendContactDao.readMsg(uid,chatId,msgId);
            case GROUP -> groupContactDao.readMsg(uid,chatId,msgId);
        }
    }
}
