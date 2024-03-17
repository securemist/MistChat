package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.dao.FriendContactDao;
import cc.xmist.mistchat.server.chat.dao.GroupContactDao;
import cc.xmist.mistchat.server.chat.entity.FriendContact;
import cc.xmist.mistchat.server.chat.entity.GroupContact;
import cc.xmist.mistchat.server.chat.model.resp.ContactListResp;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final FriendContactDao friendContactDao;
    private final GroupContactDao groupContactDao;
    private final GroupMemberDao groupMemberDao;


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
