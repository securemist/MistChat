package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
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

    public User getUser(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
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
}
