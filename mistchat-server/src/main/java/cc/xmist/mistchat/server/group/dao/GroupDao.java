package cc.xmist.mistchat.server.group.dao;

import cc.xmist.mistchat.server.common.enums.GroupStatus;
import cc.xmist.mistchat.server.group.entity.Group;
import cc.xmist.mistchat.server.group.mapper.GroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-03-14
 */
@Service
public class GroupDao extends ServiceImpl<GroupMapper, Group> {

    /**
     * 创建群聊
     *
     * @param createrId 创建者 iud
     * @param name      群名称
     * @param groupId   群聊 id
     * @return
     */
    public Group create(Long createrUid, String name, Long groupId) {
        Group group = Group.builder()
                .name(name)
                .id(groupId)
                .createrUid(createrUid)
                .ownerUid(createrUid)
                .status(GroupStatus.OK)
                .build();
        save(group);
        return group;
    }

    public boolean existsId(Long groupId) {
        return lambdaQuery()
                .eq(Group::getId, groupId)
                .count() != 0;
    }
}
