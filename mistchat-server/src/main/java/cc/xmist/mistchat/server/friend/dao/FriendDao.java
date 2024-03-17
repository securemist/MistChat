package cc.xmist.mistchat.server.friend.dao;

import cc.xmist.mistchat.server.friend.entity.Friend;
import cc.xmist.mistchat.server.friend.mapper.FriendMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户联系人表 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-27
 */
@Service
public class FriendDao extends ServiceImpl<FriendMapper, Friend> {
    @Resource
    private FriendMapper friendMapper;

    public Friend create(Long uid1, Long uid2) {
        Friend friend = Friend.builder()
                .uid1(Math.min(uid1, uid2))
                .uid2(Math.max(uid1, uid2))
                .build();
        save(friend);
        return friend;
    }


    public List<Long> getFriendIdList(Long uid) {
        List<Friend> list = lambdaQuery()
                .eq(Friend::getUid1, uid)
                .or()
                .eq(Friend::getUid2, uid)
                .isNull(Friend::getDeleteTime)
                .list();

        return list.stream().map(u -> {
            return u.getUid1() == uid ? u.getUid2() : u.getUid1();
        }).collect(Collectors.toList());
    }

    public Friend get(Long uid, Long targetUid) {
        return lambdaQuery()
                .eq(Friend::getUid1, Math.min(uid,targetUid))
                .eq(Friend::getUid2,Math.max(uid,targetUid))
                .one();
    }

    public boolean isFriend(Long uid, Long targetUid) {
        return friendMapper.isFriend(uid,targetUid);
    }
}
