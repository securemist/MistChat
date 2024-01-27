package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.RoomFriend;
import cc.xmist.mistchat.server.chat.model.mapper.RoomFriendMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-26
 */
@Service
public class RoomFriendDao extends ServiceImpl<RoomFriendMapper, RoomFriend> {

    public RoomFriend create(Long roomId, Long uid1, Long uid2) {
        Long small = uid1 < uid2 ? uid1 : uid2;
        Long big = uid1 < uid2 ? uid2 : uid1;
        RoomFriend roomFriend = RoomFriend.builder()
                .roomId(roomId)
                .uid1(small)
                .uid2(big)
                .build();
        save(roomFriend);
        return roomFriend;
    }

    public List<Long> getMembers(Long roomId) {
        RoomFriend roomFriend = lambdaQuery()
                .eq(RoomFriend::getRoomId, roomId)
                .one();
        return Arrays.asList(roomFriend.getUid1(),roomFriend.getUid2());
    }
}
