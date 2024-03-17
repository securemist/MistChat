package cc.xmist.mistchat.server.chat.model.resp;

import cc.xmist.mistchat.server.chat.entity.FriendContact;
import cc.xmist.mistchat.server.chat.entity.GroupContact;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContactListResp {
    private List<FriendContact> friendContacts;
    private List<GroupContact> groupContacts;
}
