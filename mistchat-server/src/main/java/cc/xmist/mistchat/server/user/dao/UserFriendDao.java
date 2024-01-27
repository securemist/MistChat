package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.model.entity.UserFriend;
import cc.xmist.mistchat.server.user.model.mapper.UserFriendMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    public void create(Long uid, Long targetId) {
        UserFriend friend1 = UserFriend.builder()
                .uid(uid)
                .friendUid(targetId)
                .build();

        UserFriend friend2 = UserFriend.builder()
                .uid(targetId)
                .friendUid(uid)
                .build();

        saveBatch(Arrays.asList(friend1, friend2));
    }

    public Boolean isFriend(Long uid, Long friendId) {
        return lambdaQuery()
                .eq(UserFriend::getUid, uid)
                .eq(UserFriend::getFriendUid, friendId)
                .exists();
    }
}
