package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2024-01-11
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByName(String name) {
        return lambdaQuery()
                .eq(User::getName, name)
                .one();
    }

    public void addUser(String username, String password, String name) {
        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .build();
        user.insert();
    }

    public User getByUsername(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .one();
    }

    public User getUser(Long uid) {
        return getById(uid);
    }

    public boolean modifyName(Long uid, String name) {
        return lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getName, name)
                .update();
    }

    public void wearBadge(Long uid, Long badgeId) {
        lambdaUpdate()
                .set(User::getItemId, badgeId)
                .eq(User::getId, uid)
                .update();
    }
}
