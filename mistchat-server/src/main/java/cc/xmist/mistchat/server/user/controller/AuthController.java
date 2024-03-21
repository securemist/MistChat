package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.req.RegisterRequest;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "权限")
@RequestMapping("/user/auth")
public class AuthController {
    @Resource
    private AuthService authService;
    @Resource
    private UserService userService;

    @PostMapping("/public/register")
    @Operation(summary = "注册")
    public R register(@RequestBody RegisterRequest req) {
        userService.register(req);
        return R.ok(null);
    }

    @PostMapping("/public/login")
    @Operation(summary = "登录")
    public R<String> login(@RequestBody cc.xmist.mistchat.server.user.model.req.LoginRequest loginReq) {
        User user = userService.login(loginReq.getUsername(), loginReq.getPassword());
        // 签发token
        String token = authService.login(user.getId());
        return R.ok(token);
    }
}
