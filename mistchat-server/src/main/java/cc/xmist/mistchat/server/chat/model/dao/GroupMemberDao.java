package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.GroupMember;
import cc.xmist.mistchat.server.chat.model.mapper.GroupMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {

    public List<Long> getMembers(Long groupId) {
        List<GroupMember> members = lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .select(GroupMember::getUid)
                .list();

        return members.stream()
                .map(member -> member.getUid())
                .collect(Collectors.toList());
    }

    /**
     * 用户加入的群聊
     *
     * @param uid
     * @return
     */
    public List<Long> getBelongingGroupsId(Long uid) {
        List<GroupMember> list = lambdaQuery()
                .eq(GroupMember::getUid, uid)
                .select(GroupMember::getGroupId)
                .list();

        return  list.stream().map(GroupMember::getGroupId).collect(Collectors.toList());
    }
}
