package cc.xmist.mistchat.server.group.sevrice;

import cc.xmist.mistchat.server.chat.dao.GroupContactDao;
import cc.xmist.mistchat.server.common.event.MemberChangeEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.group.dao.GroupDao;
import cc.xmist.mistchat.server.group.dao.GroupMemberDao;
import cc.xmist.mistchat.server.group.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupDao groupDao;
    private final GroupMemberDao groupMemberDao;
    private final ApplicationEventPublisher eventPublisher;
    private final GroupContactDao groupContactDao;

    /**
     * 用户加入群聊
     *
     * @param uid
     * @param groupId
     */
    public void join(Long uid, Long groupId) {
        if (groupMemberDao.belong(uid, groupId)) throw new BusinessException("请勿重复加入群组");
        groupMemberDao.join(uid, groupId);
        eventPublisher.publishEvent(new MemberChangeEvent(this, uid, groupId, false));
    }

    /**
     * 用户退出群聊
     *
     * @param uid
     * @param groupId
     */
    public void exit(Long uid, Long groupId) {
        Group group = groupDao.getById(groupId);
        if(group.getOwnerUid().equals(uid)) throw new BusinessException("群主不能退出群聊");

        groupMemberDao.removeUser(uid, groupId);
        groupContactDao.remove(uid,groupId);
        eventPublisher.publishEvent(new MemberChangeEvent(this, uid, groupId, true));
    }
}
