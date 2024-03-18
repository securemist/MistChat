package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.dao.GroupContactDao;
import cc.xmist.mistchat.server.chat.entity.FriendContact;
import cc.xmist.mistchat.server.chat.entity.GroupContact;
import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.model.resp.ContactListResp;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public void updateFriendContact(Long uid, Long friendId, Message message) {

    }

    public void updateGroupContact(Long uid,Long groupId, Message message){

    }

}
