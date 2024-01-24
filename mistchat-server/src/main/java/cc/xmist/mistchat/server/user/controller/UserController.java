package cc.xmist.mistchat.server.user.controller;


import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.model.req.LoginReq;
import cc.xmist.mistchat.server.user.model.req.ModifyNameReq;
import cc.xmist.mistchat.server.user.model.req.RegisterReq;
import cc.xmist.mistchat.server.user.model.resp.BadgeVo;
import cc.xmist.mistchat.server.user.model.resp.UserInfoResponse;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.BadgeService;
import cc.xmist.mistchat.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "用户接口")
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private AuthService authService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public R<UserInfoResponse> getUserInfo() {
        Long uid = RequestContext.getUid();
        UserInfoResponse userInfo = userService.getUserInfo(uid);
        return R.ok(userInfo);
    }

    @Operation(summary = "修改用户名称")
    @PostMapping("/name/modify")
    public R<Void> modifyName(@RequestBody @Valid ModifyNameReq modifyNameReq) {
        Long uid = RequestContext.getUid();
        userService.modifyName(uid, modifyNameReq.getName());
        return R.ok();
    }

    @PostMapping("/public/register")
    public R register(@RequestBody RegisterReq registerReq) {
        userService.register(registerReq.getUsername(), registerReq.getPassword(), registerReq.getName());
        return R.ok(null);
    }

    @PostMapping("/public/login")
    public R login(@RequestBody LoginReq loginReq) {
        User user = userService.login(loginReq.getUsername(), loginReq.getPassword());
        // 签发token
        String token = authService.login(user.getId());
        return R.ok(token);
    }
}

