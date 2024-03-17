package cc.xmist.mistchat.server.group.model.dao;

import cc.xmist.mistchat.server.group.model.entity.Group;
import cc.xmist.mistchat.server.group.model.enums.GroupStatus;
import cc.xmist.mistchat.server.group.model.mapper.GroupMapper;
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

    public Group create(Long createrId, String name) {
        Group group = Group.builder()
                .name(name)
                .createrUid(createrId)
                .ownerUid(createrId)
                .status(GroupStatus.OK)
                .build();
        save(group);
        return group;
    }
}
