package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.GroupContact;
import cc.xmist.mistchat.server.chat.mapper.GroupContactMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupContactDao extends ServiceImpl<GroupContactMapper, GroupContact> {
    /**
     * 多个用户首次加入群聊，批量创建会话
     *
     * @param id
     * @param uidList
     */
    public void createBatch(Long groupId, List<Long> uidList) {
        List<GroupContact> contactList = uidList.stream()
                .map(uid -> {
                    return GroupContact.builder()
                            .uid(uid)
                            .groupId(groupId)
                            .build();
                })
                .collect(Collectors.toList());
        saveBatch(contactList);
    }

    public void remove(Long uid, Long groupId) {
        LambdaQueryWrapper<GroupContact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GroupContact::getUid, uid);
        wrapper.eq(GroupContact::getGroupId, groupId);
        baseMapper.delete(wrapper);
    }

    /**
     * 更新用户在群聊里的活跃状态
     *
     * @param uid     用户 id
     * @param groupId 群聊 id
     * @param msgId   最新的消息 id
     */
    public void updateActive(Long uid, Long groupId, Long msgId) {
        lambdaUpdate()
                .set(GroupContact::getLastMsgId, msgId)
                .eq(GroupContact::getUid, uid)
                .eq(GroupContact::getGroupId, groupId)
                .update();
    }

    /**
     * 用户已读群聊的消息
     * @param uid
     * @param groupId
     * @param msgId
     */
    public void readMsg(Long uid, Long groupId, Long msgId) {
        lambdaUpdate()
                .set(GroupContact::getReadMsgId, msgId)
                .eq(GroupContact::getUid, uid)
                .eq(GroupContact::getGroupId, groupId)
                .update();
    }
}
