package cc.xmist.mistchat.server.user.controller;


import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.model.req.LoginReq;
import cc.xmist.mistchat.server.user.model.req.RegisterReq;
import cc.xmist.mistchat.server.user.model.resp.UserInfoResponse;
import cc.xmist.mistchat.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "用户接口")
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/userInfo")
    public R<UserInfoResponse> getUserInfo() {
        Long uid = RequestContext.getUid();
        String ip = RequestContext.getIp();
        UserInfoResponse userInfo = userService.getUserInfo(uid);
        return R.ok(userInfo);
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

