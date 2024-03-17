package cc.xmist.mistchat.server.group.model.dao;

import cc.xmist.mistchat.server.group.model.entity.Group;
import cc.xmist.mistchat.server.group.model.entity.GroupMember;
import cc.xmist.mistchat.server.group.model.mapper.GroupMemberMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {

    /**
     * 获取一个群聊的所有成员
     * @param groupId
     * @return
     */
    public List<Long> getMembers(Long groupId) {
        List<GroupMember> members = lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .list();

        return members.stream()
                .map(member -> member.getUid())
                .collect(Collectors.toList());
    }

    /**
     * 获取用户加入的群聊列表
     * @param uid
     * @return
     */
    public List<Long> getBelongingGroupsId(Long uid) {
        List<GroupMember> list = lambdaQuery()
                .eq(GroupMember::getUid, uid)
                .select(GroupMember::getGroupId)
                .list();

        return list.stream().map(GroupMember::getGroupId).collect(Collectors.toList());
    }

    /**
     * 用户加入群聊
     * @param uid
     * @param groupId
     */
    public void join(Long uid, Long groupId) {
        GroupMember groupMember = GroupMember.builder()
                .uid(uid)
                .groupId(groupId)
                .build();

        save(groupMember);
    }

    /**
     * 判断某个用户是否属于某个群聊
     * @param uid
     * @param groupId
     * @return
     */
    public boolean belong(Long uid, Long groupId) {
        return lambdaQuery()
                .eq(GroupMember::getUid, uid)
                .eq(GroupMember::getGroupId, groupId)
                .exists();
    }

    /**
     * 首次创建的群聊，将初始用户拉入群聊
     * @param groupId
     * @param uidList
     */
    public void addMembers(Long groupId, List<Long> uidList) {
        List<GroupMember> groupMembers = uidList.stream()
                .map(uid -> {
                    return GroupMember.builder()
                            .groupId(groupId)
                            .uid(uid)
                            .build();
                }).collect(Collectors.toList());
        saveBatch(groupMembers);
    }
}
