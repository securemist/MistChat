package cc.xmist.mistchat.server.friend.dao;

import cc.xmist.mistchat.server.common.exception.DeleteFailedException;
import cc.xmist.mistchat.server.friend.entity.Friend;
import cc.xmist.mistchat.server.friend.mapper.FriendMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    public Friend create(Integer uid1, Integer uid2) {
        Friend friend = new Friend(uid1, uid2);
        save(friend);
        return friend;
    }


    public List<Integer> listFriendsId(Integer uid) {
        List<Friend> list = lambdaQuery()
                .eq(Friend::getUid1, uid)
                .or()
                .eq(Friend::getUid2, uid)
                .isNull(Friend::getDeleteTime)
                .list();

        return list.stream()
                .map(u -> u.getUid1() == uid ? u.getUid2() : u.getUid1())
                .collect(Collectors.toList());
    }

    public Friend get(Integer uid, Integer targetUid) {
        return lambdaQuery()
                .eq(Friend::getUid1, Math.min(uid, targetUid))
                .eq(Friend::getUid2, Math.max(uid, targetUid))
                .isNotNull(Friend::getDeleteTime)
                .one();
    }

    public boolean isFriend(Integer uid, Integer targetUid) {
        return lambdaQuery()
                .select(Friend::getId)
                .eq(Friend::getUid1, uid)
                .eq(Friend::getUid2, targetUid)
                .isNotNull(Friend::getDeleteTime)
                .or(wrapper -> {
                    wrapper.eq(Friend::getUid1, targetUid)
                            .eq(Friend::getUid2, uid);
                })
                .count() != 0;
    }

    public void delete(Integer uid, Integer friendUid) {
        LambdaQueryWrapper<Friend> wrapper = new LambdaQueryWrapper<>();
        String roomId = Friend.getRoomId(uid, friendUid);

        wrapper.eq(Friend::getRoomId,roomId);

        boolean ok = baseMapper.delete(wrapper) == 1;
        if (!ok) throw new DeleteFailedException();
    }
}
