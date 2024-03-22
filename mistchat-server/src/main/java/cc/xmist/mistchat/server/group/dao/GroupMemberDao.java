package cc.xmist.mistchat.server.group.dao;

import cc.xmist.mistchat.server.group.entity.GroupMember;
import cc.xmist.mistchat.server.group.mapper.GroupMemberMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {

    /**
     * 获取一个群聊的所有成员
     *
     * @param groupId
     * @param cursor
     * @param pageSize
     * @return
     */
    public List<GroupMember> getMembersCursorable(String roomId, String cursor, Integer pageSize) {
        return lambdaQuery()
                .select(GroupMember::getId, GroupMember::getUid)
                .lt(cursor != null, GroupMember::getId, cursor)
                .orderByDesc(GroupMember::getId)
                .last("LIMIT " + pageSize)
                .list();
    }

    /**
     * 直接获取群聊所有成员
     *
     * @param groupId
     * @return
     */
    public List<Integer> getMembers(String roomId) {
        List<GroupMember> list = lambdaQuery()
                .eq(GroupMember::getGroupId, roomId)
                .list();
        return list.stream().map(GroupMember::getUid).collect(Collectors.toList());
    }

    /**
     * 获取用户加入的群聊列表
     *
     * @param uid
     * @return
     */
    public List<String > getBelongingGroupsId(Integer uid) {
        List<GroupMember> list = lambdaQuery()
                .eq(GroupMember::getUid, uid)
                .list();

        return list.stream().map(GroupMember::getGroupId).collect(Collectors.toList());
    }

    /**
     * 用户加入群聊
     *
     * @param uid
     * @param groupId
     */
    public void join(Integer uid, String  groupId) {
        GroupMember member = GroupMember.builder()
                .groupId(groupId)
                .uid(uid)
                .build();
        save(member);
    }

    /**
     * 判断某个用户是否属于某个群聊
     *
     * @param uid
     * @param groupId
     * @return
     */
    public boolean belong(Integer uid, String  groupId) {
        return lambdaQuery()
                .eq(GroupMember::getUid, uid)
                .eq(GroupMember::getGroupId, groupId)
                .exists();
    }

    /**
     * 首次创建的群聊，将初始用户拉入群聊
     *
     * @param groupId
     * @param uidList
     */
    public void addMembers(String  groupId, List<Integer> uidList) {
        List<GroupMember> groupMembers = uidList.stream()
                .map(uid -> {
                    return GroupMember.builder()
                            .groupId(groupId)
                            .uid(uid)
                            .build();
                }).collect(Collectors.toList());
        saveBatch(groupMembers);
    }

    /**
     * 将用户移出群聊
     *
     * @param uid
     * @param groupId
     */
    public void removeUser(Integer uid, String groupId) {
        LambdaQueryWrapper<GroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GroupMember::getGroupId, groupId);
        wrapper.eq(GroupMember::getUid, uid);
        baseMapper.delete(wrapper);
    }

    /**
     * 批量获取所有群的群成员
     *
     * @param groupsId
     * @return
     */
    public Map<String, List<Integer>> getMembersBatch(List<String > groupsId) {
        List<GroupMember> list = lambdaQuery()
                .in(GroupMember::getGroupId, groupsId)
                .list();


        Map<String , List<GroupMember>> memberMap = list.stream()
                .collect(Collectors.groupingBy(GroupMember::getGroupId));

        Map<String, List<Integer>> res = new HashMap<>();
        memberMap.forEach((groupId, members) -> {
            List<Integer> uids = members.stream().map(GroupMember::getUid).collect(Collectors.toList());
            res.put(groupId, uids);
        });
        return res;
    }

    /**
     * 获取用户加入的所有群聊的 id
     *
     * @param uid
     * @return
     */
    public List<String > getGroupsId(Integer uid) {
        List<GroupMember> list = lambdaQuery()
                .eq(GroupMember::getId, uid)
                .select(GroupMember::getGroupId)
                .list();

        return list.stream().map(GroupMember::getGroupId).collect(Collectors.toList());
    }

    /**
     * 更新用户在群聊里的活跃时间
     *
     * @param uid
     * @param groupId
     */
    public void updateActive(Integer uid, String  groupId) {
        lambdaUpdate()
                .set(GroupMember::getActiveTime, LocalDateTime.now())
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getUid, uid)
                .update();
    }

    /**
     * 用户加入群聊时初始化成员信息
     *
     * @param groupId
     * @param uids
     */
    public void initMembers(String  groupId, List<Integer> uids) {
        List<GroupMember> members = uids.stream().map(uid -> {
            return GroupMember.builder()
                    .groupId(groupId)
                    .uid(uid)
                    .build();
        }).collect(Collectors.toList());

        saveBatch(members);
    }
}
