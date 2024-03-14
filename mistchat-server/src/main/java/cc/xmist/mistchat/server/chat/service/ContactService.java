package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.model.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.model.dao.GroupContactDao;
import cc.xmist.mistchat.server.chat.model.dao.GroupMemberDao;
import cc.xmist.mistchat.server.chat.model.entity.FriendContact;
import cc.xmist.mistchat.server.chat.model.entity.GroupContact;
import cc.xmist.mistchat.server.chat.model.resp.ContactListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private FriendContactDao friendContactDao;
    private GroupContactDao groupContactDao;
    private GroupMemberDao groupMemberDao;

    @Autowired
    public ContactService(FriendContactDao friendContactDao, GroupContactDao groupContactDao, GroupMemberDao groupMemberDao) {
        this.friendContactDao = friendContactDao;
        this.groupContactDao = groupContactDao;
        this.groupMemberDao = groupMemberDao;
    }

    public ContactListResp getContactList(Long uid) {
        List<FriendContact> friendContacts = friendContactDao.getByUid(uid);

        List<Long> groupsId = groupMemberDao.getBelongingGroupsId(uid);
        List<GroupContact> groupContacts = groupContactDao.listByIds(groupsId);

        return ContactListResp.builder()
                .friendContacts(friendContacts)
                .groupContacts(groupContacts)
                .build();
    }

}
