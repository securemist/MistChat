package cc.xmist.mistchat.server.group.sevrice;

import cc.xmist.mistchat.server.chat.model.dao.GroupContactDao;
import cc.xmist.mistchat.server.common.event.GroupAddEvent;
import cc.xmist.mistchat.server.group.model.dao.GroupApplyDao;
import cc.xmist.mistchat.server.group.model.dao.GroupDao;
import cc.xmist.mistchat.server.group.model.dao.GroupMemberDao;
import cc.xmist.mistchat.server.group.model.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {
    private GroupMemberDao groupMemberDao;
    private GroupDao groupDao;
    private GroupApplyDao groupApplyDao;
    private GroupContactDao groupContactDao;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public GroupService(GroupMemberDao groupMemberDao, GroupDao groupDao, GroupApplyDao groupApplyDao, GroupContactDao groupContactDao, ApplicationEventPublisher eventPublisher) {
        this.groupMemberDao = groupMemberDao;
        this.groupDao = groupDao;
        this.groupApplyDao = groupApplyDao;
        this.groupContactDao = groupContactDao;
        this.eventPublisher = eventPublisher;
    }


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

