package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.RoomFriend;
import cc.xmist.mistchat.server.chat.model.mapper.RoomFriendMapper;
import cc.xmist.mistchat.server.user.dao.UserFriendDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    @Resource
    private UserFriendDao userFriendDao;
    /**
     * 双人聊天室
     * 两个人的聊天室，有两个 uid1 和 uid2
     * 规定小的 uid 为 uid1，大的 uid 为 uid2，这样保证两个人只会创建一个聊天室
     *
     * @param roomId
     * @param uid1
     * @param uid2
     * @param friendId
     * @return
     */
    public RoomFriend create(Long uid1, Long uid2) {
        RoomFriend roomFriend = RoomFriend.builder().build();
        save(roomFriend);
        return roomFriend;
    }

    public void create(Long friendId) {
        RoomFriend roomFriend = RoomFriend.builder()
                .friendId(friendId)
                .build();
        save(roomFriend);
    }


    public Long getId(Long uid, Long targetUid) {
        return  userFriendDao.get(uid,targetUid).getId();
    }
}
