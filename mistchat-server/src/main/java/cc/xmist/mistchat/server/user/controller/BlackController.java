package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.req.BlockRequest;
import cc.xmist.mistchat.server.user.service.BlackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "用户接口")
@RequestMapping("/user/black")
public class BlackController {
    @Resource
    private BlackService blackService;

    @Operation(summary = "拉黑操作")
    @PostMapping("/add")
    public R add(@RequestBody @Valid BlockRequest request) {
        blackService.block(request.getUid(),request.getType());
        return R.ok();
    }
}
