package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.event.UserRegisterEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.friend.dao.FriendDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.req.RegisterRequest;
import cc.xmist.mistchat.server.user.resp.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final ApplicationEventPublisher eventPublisher;
    private final FriendDao userFriendDao;
    private final AuthService authService;
    private final UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterRequest req) {
        if (userDao.existUsername(req.getUsername())) throw new BusinessException("该用户已注册");
        if (userDao.existName(req.getName())) throw new BusinessException("该用户名已存在");
        User user = userDao.createUser(req);
        eventPublisher.publishEvent(new UserRegisterEvent(this, user));
        log.info("{}用户完成注册，username：{}", req.getName(), req.getUsername());
    }


    public User login(String username, String password) {
        User user = userDao.getByUsername(username);
        if (user == null) throw new BusinessException("用户名不存在");
        if (!user.getPassword().equals(password)) throw new BusinessException("密码错误");

        String token = authService.login(user.getId());
        user.setToken(token);
        return user;
    }

    public UserResponse getUserInfo(Integer uid) {
        User u = userDao.getByUid(uid);
        return UserResponse.build(u);
    }


    /**
     * 修改用户用户名
     *
     * @param uid
     * @param name 新用户名
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyName(Integer uid, String name) {
        User user = userDao.getByUid(uid);
        if (userDao.existName(name)) throw new BusinessException("该用户名已存在");
        userDao.modifyName(uid, name);
    }


    /**
     * 批量获取用户信息
     *
     * @param uid
     * @param uidList
     * @return
     */
    public List<UserResponse> getBatchedUserInfo(Integer uid, List<Integer> uidList) {
        List<User> users = userDao.listByUid(uidList);

        return users.stream()
                .filter(u -> u.getId() != uid)
                .map(u -> UserResponse.build(u))
                .collect(Collectors.toList());
    }


}
