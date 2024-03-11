package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.model.entity.UserFriend;
import cc.xmist.mistchat.server.user.model.mapper.UserFriendMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    public void create(Long uid1, Long uid2) {
        UserFriend friend1 = UserFriend.builder()
                .uid(uid1)
                .friendUid(uid2)
                .build();

        UserFriend friend2 = UserFriend.builder()
                .uid(uid2)
                .friendUid(uid1)
                .build();

        saveBatch(Arrays.asList(friend1, friend2));
    }


    public List<UserFriend> getFriendList(Long uid) {
        return lambdaQuery()
                .eq(UserFriend::getUid, uid)
                .isNull(UserFriend::getDeleteTime)
                .list();
    }
}
