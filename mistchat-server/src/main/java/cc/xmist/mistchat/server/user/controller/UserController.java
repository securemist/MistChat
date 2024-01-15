package cc.xmist.mistchat.server.user.controller;


import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.LoginReq;
import cc.xmist.mistchat.server.user.model.RegisterReq;
import cc.xmist.mistchat.server.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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

    @PostMapping("/public/register")
    public R register(@RequestBody RegisterReq registerReq) {
        userService.register(registerReq.getUsername(), registerReq.getPassword(), registerReq.getName());
        return R.ok(null);
    }

    @PostMapping("/public/login")
    public R login(@RequestBody LoginReq loginReq) {
        String token = userService.login(loginReq.getUsername(), loginReq.getPassword());
        return R.ok(token);
    }
}

