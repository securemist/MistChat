package cc.xmist.mistchat.server.user.controller;


import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.req.ModifyNameReq;
import cc.xmist.mistchat.server.user.model.req.UidListReq;
import cc.xmist.mistchat.server.user.model.resp.SummaryUser;
import cc.xmist.mistchat.server.user.model.resp.UserInfoVo;
import cc.xmist.mistchat.server.user.service.AuthService;
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

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public R<UserInfoVo> getUserInfo(Long uid) {
        UserInfoVo userInfo = userService.getUserInfo(uid);
        return R.ok(userInfo);
    }

    @Operation(summary = "其他用户信息")
    @PostMapping("/another/info/list")
    public R<List<SummaryUser>> getUserInfoBatched(@RequestBody UidListReq req, Long uid) {
        return R.ok(userService.getBatchedUserInfo(uid, req.getUidList()));
    }

    @Operation(summary = "修改用户名称")
    @PostMapping("/name/modify")
    public R<Void> modifyName(@RequestBody @Valid ModifyNameReq modifyNameReq, Long uid) {
        userService.modifyName(uid, modifyNameReq.getName());
        return R.ok();
    }
}

