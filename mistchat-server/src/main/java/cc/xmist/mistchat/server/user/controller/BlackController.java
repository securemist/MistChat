package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.enums.Role;
import cc.xmist.mistchat.server.common.util.ErrorType;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.service.BlackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.xmist.mistchat.server.user.model.req.BlackRequest ;

@RestController
@Tag(name = "管理员接口")
@RequestMapping("/user/black")
public class BlackController {
    @Resource
    private BlackService blackService;

    @Operation(summary = "拉黑操作")
    @PostMapping("/add")
    public R add(@RequestBody @Valid BlackRequest request) {
        Integer uid = RequestContext.getUid();
        Role role = RequestContext.getUser().getRole();
        blackService.block(uid, request.getTargetUid(), request.getType());
        return R.ok();
    }
}
