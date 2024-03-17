package cc.xmist.mistchat.server.group.sevrice;

import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.group.model.dao.GroupMemberDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberService {

    @Resource
    private GroupMemberDao groupMemberDao;

    public void join(Long uid, Long groupId) {
        if(groupMemberDao.belong(uid,groupId)) throw new BusinessException("请勿重复加入群组");
        groupMemberDao.join(uid, groupId);
    }
}
