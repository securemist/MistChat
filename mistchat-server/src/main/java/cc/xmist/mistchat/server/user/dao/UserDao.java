package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.common.enums.Role;
import cc.xmist.mistchat.server.common.enums.UserStatus;
import cc.xmist.mistchat.server.common.util.JsonUtil;
import cc.xmist.mistchat.server.user.entity.IpInfo;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.mapper.UserMapper;
import cc.xmist.mistchat.server.user.model.req.RegisterRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public User createUser(RegisterRequest req) {
        User u = User.builder()
                .name(req.getName())
                .username(req.getUsername())
                .password(req.getPassword())
                .gender(req.getGender())
                .status(UserStatus.NORMAL)
                .role(Role.USER)
                .build();
        save(u);
        return u;
    }


    public User getByUsername(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .one();
    }

    public User getByUid(Integer uid) {
        return lambdaQuery()
                .eq(User::getId, uid)
                .one();
    }

    public boolean modifyName(Integer uid, String name) {
        return lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getName, name)
                .update();
    }


    public void updateIpInfo(Integer uid, IpInfo ipInfo) {
        lambdaUpdate()
                .set(User::getIpInfo, JsonUtil.toJson(ipInfo))
                .eq(User::getId, uid)
                .update();
    }

    public boolean existName(String name) {
        return lambdaQuery()
                .eq(User::getName, name)
                .count() != 0;
    }

    public boolean existUsername(String username) {
        return lambdaQuery()
                .eq(User::getName, username)
                .count() != 0;
    }


    public List<User> listByUid(List<Integer> uidList) {
        return lambdaQuery()
                .getBaseMapper()
                .selectBatchIds(uidList);
    }
}
