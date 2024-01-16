package cc.xmist.mistchat.server.user.controller;


import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.model.req.LoginReq;
import cc.xmist.mistchat.server.user.model.req.RegisterReq;
import cc.xmist.mistchat.server.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @since 2024-01-11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    public R getUserInfo() {
        Long uid = RequestContext.getUid();
        String ip = RequestContext.getIp();
        return R.ok(null);
    }

    @PostMapping("/public/register")
    public R register(@RequestBody RegisterReq registerReq) {
        userService.register(registerReq.getUsername(), registerReq.getPassword(), registerReq.getName());
        return R.ok(null);
    }

    @PostMapping("/public/login")
    public R login(@RequestBody LoginReq loginReq) {
        User user = userService.login(loginReq.getUsername(), loginReq.getPassword());
        return R.ok(null);
    }
}

