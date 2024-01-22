package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.util.JwtUtil;
import cc.xmist.mistchat.server.user.dao.UserBackpackDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.model.enums.ItemType;
import cc.xmist.mistchat.server.user.model.resp.UserInfoResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private AuthService authService;
    @Resource
    private UserBackpackDao userBackpackDao;
    public void register(String username, String password, String name) {
        User user = userDao.getUser(username);
        if (user != null) {
            throw new RuntimeException("该用户已注册");
        }

        userDao.addUser(username, password, name);
        log.info("{}用户完成注册，username：{}", name, username);
    }

    public User login(String username, String password) {
        User user = userDao.getUser(username);
        if (user == null) {
            throw new BusinessException("用户名不存在");
        }

        if (!user.getPassword().equals(password)) {
            throw new BusinessException("密码错误");
        }

        String token = authService.login(user.getId());
        log.info("用户 {} 登陆成功",user.getName());

        return user;
    }

    public UserInfoResponse getUserInfo(Long uid) {
        User user = userDao.getUser(uid);
        Long modifyNameCount = userBackpackDao.getItemCount(uid, ItemType.MODIFY_NAME_CARD);

        return UserInfoResponse.builder()
                .id(user.getId())
                .sex(user.getSex())
                .avatar(user.getAvatar())
                .name(user.getName())
                .modifyNameChance(modifyNameCount).build();
    }

    /**
     * 修改用户用户名
     * @param uid
     * @param name 新用户名
     */
    public void modifyName(Long uid, String name) {

    }
}
