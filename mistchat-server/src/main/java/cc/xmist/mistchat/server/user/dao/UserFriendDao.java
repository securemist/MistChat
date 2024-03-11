package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.model.entity.UserFriend;
import cc.xmist.mistchat.server.user.model.mapper.UserFriendMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {

    public UserFriend create(Long uid1, Long uid2) {
        UserFriend friend = UserFriend.builder()
                .uid1(Math.min(uid1, uid2))
                .uid2(Math.max(uid1, uid2))
                .build();
        save(friend);
        return friend;
    }


    public List<Long> getFriendIdList(Long uid) {
        List<UserFriend> list = lambdaQuery()
                .eq(UserFriend::getUid1, uid)
                .or()
                .eq(UserFriend::getUid2, uid)
                .isNull(UserFriend::getDeleteTime)
                .list();

        return list.stream().map(u -> {
            return u.getUid1() == uid ? u.getUid2() : u.getUid1();
        }).collect(Collectors.toList());
    }

    public UserFriend get(Long uid, Long targetUid) {
        return lambdaQuery()
                .eq(UserFriend::getUid1, Math.min(uid,targetUid))
                .eq(UserFriend::getUid2,Math.max(uid,targetUid))
                .one();
    }
}
