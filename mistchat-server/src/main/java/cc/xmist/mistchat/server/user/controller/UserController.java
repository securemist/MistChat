package cc.xmist.mistchat.server.user.controller;


import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.req.AnotherUserInfoRequest;
import cc.xmist.mistchat.server.user.model.req.LoginRequest;
import cc.xmist.mistchat.server.user.model.req.ModifyNameRequest;
import cc.xmist.mistchat.server.user.model.req.RegisterRequest;
import cc.xmist.mistchat.server.user.model.vo.SummaryUser;
import cc.xmist.mistchat.server.user.model.vo.BadgeVo;
import cc.xmist.mistchat.server.user.model.vo.UserInfoVo;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.ItemService;
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
    @Resource
    private ItemService badgeService;


    @Operation(summary = "获取用户信息")
    @GetMapping("/own/info")
    public R<UserInfoVo> getUserInfo() {
        Long uid = RequestContext.getUid();
        UserInfoVo userInfo = userService.getUserInfo(uid);
        return R.ok(userInfo);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/own/info1")
    public R<UserInfoVo> getUserInfo1(UserInfoVo userInfoVo) {
        Long uid = RequestContext.getUid();
        UserInfoVo userInfo = userService.getUserInfo(uid);
        return R.ok(userInfo);
    }

    @Operation(summary = "批量获取其它用户信息")
    @PostMapping("/another/info/list")
    public R<List<SummaryUser>> getUserInfoBatched(@RequestBody @Valid AnotherUserInfoRequest request) {
        Long uid = RequestContext.getUid();
        return R.ok(userService.getSummaryUsers(request.getUidList()));
    }


    @Operation(summary = "修改用户名称")
    @PostMapping("/name/modify")
    public R<Void> modifyName(@RequestBody @Valid ModifyNameRequest modifyNameReq) {
        Long uid = RequestContext.getUid();
        userService.modifyName(uid, modifyNameReq.getName());
        return R.ok();
    }

    @PostMapping("/public/register")
    @Operation(summary = "注册")
    public R register(@RequestBody RegisterRequest registerReq) {
        userService.register(registerReq.getUsername(), registerReq.getPassword(), registerReq.getName());
        return R.ok(null);
    }

    @PostMapping("/public/login")
    @Operation(summary = "登录")
    public R login(@RequestBody LoginRequest loginReq) {
        User user = userService.login(loginReq.getUsername(), loginReq.getPassword());
        // 签发token
        String token = authService.login(user.getId());
        return R.ok(token);
    }

    @Operation(summary = "获取用户徽章列表")
    @GetMapping("/badges")
    public R<List<BadgeVo>> getBadges() {
        Long uid = RequestContext.getUid();
        return R.ok(badgeService.getBadgeList(uid));
    }


    @Operation(summary = "佩戴徽章")
    @PostMapping("/badge/wear/{badgeId}")
    public R wearBadge(@PathVariable Long badgeId) {
        Long uid = RequestContext.getUid();
        badgeService.wearBadge(uid, badgeId);
        return R.ok();
    }

}

