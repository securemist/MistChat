package cc.xmist.mistchat.server.group.sevrice;

import cc.xmist.mistchat.server.chat.model.dao.GroupContactDao;
import cc.xmist.mistchat.server.common.event.GroupAddEvent;
import cc.xmist.mistchat.server.group.model.dao.GroupApplyDao;
import cc.xmist.mistchat.server.group.model.dao.GroupDao;
import cc.xmist.mistchat.server.group.model.dao.GroupMemberDao;
import cc.xmist.mistchat.server.group.model.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMemberDao groupMemberDao;
    private final GroupDao groupDao;
    private final GroupApplyDao groupApplyDao;
    private final GroupContactDao groupContactDao;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 获取群聊所有成员
     * @param groupId
     * @return
     */
    public List<Long> getMembers(Long groupId) {
       return groupMemberDao.getMembers(groupId);
    }


    /**
     * 创建群聊
     *
     * @param createrId
     * @param name
     * @param uidList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Long create(Long createrId, String name, List<Long> uidList) {
        Group group = groupDao.create(createrId, name);
        groupMemberDao.addMembers(group.getId(), uidList);
        groupContactDao.createBatch(group.getId(), uidList);
        eventPublisher.publishEvent(new GroupAddEvent(this, group, uidList));
        return group.getId();
    }
}

