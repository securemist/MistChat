package cc.xmist.mistchat.server.user.dao;

import cc.xmist.mistchat.server.common.enums.ActiveType;
import cc.xmist.mistchat.server.common.enums.Role;
import cc.xmist.mistchat.server.common.util.JsonUtil;
import cc.xmist.mistchat.server.user.entity.IpInfo;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Resource
    private UserMapper userMapper;

    public User getByName(String name) {
        return lambdaQuery()
                .eq(User::getName, name)
                .one();
    }

    public User addUser(String username, String password, String name) {
        User user = User.builder()
                .username(username)
                .password(password)
                .role(Role.USER)
                .activeStatus(ActiveType.OFF)
                .name(name)
                .build();
        save(user);
        return user;
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

    public void updateIpInfo(Long uid, IpInfo ipInfo) {
        lambdaUpdate()
                .set(User::getIpInfo, JsonUtil.toJson(ipInfo))
                .eq(User::getId, uid)
                .update();
    }

    public void online(Long uid) {
        lambdaUpdate()
                .set(User::getActiveStatus, ActiveType.ON)
                .set(User::getLastOptTime, new Date())
                .eq(User::getId, uid)
                .update();
    }

    public List<User> getUserBatch(List<Long> uidList) {
        return lambdaQuery()
                .getBaseMapper()
                .selectBatchIds(uidList);
    }
}
