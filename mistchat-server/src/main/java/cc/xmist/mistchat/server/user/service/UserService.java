package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.jwt.JwtUtil;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.entity.User;
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
}
