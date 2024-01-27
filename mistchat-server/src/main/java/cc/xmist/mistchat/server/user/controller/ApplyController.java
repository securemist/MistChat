package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.req.ApplyAddRequest;
import cc.xmist.mistchat.server.user.model.req.ApplyHandleRequest;
import cc.xmist.mistchat.server.user.service.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/apply")
@Tag(name = "好友接口")
public class ApplyController {
    @Resource
    private ApplyService applyService;

    @PostMapping("/add")
    @Operation(summary = "添加好友申请")
    public R add(@RequestBody @Valid ApplyAddRequest request) {
        Long uid = RequestContext.getUid();
        applyService.createApply(uid, request);
        return R.ok();
    }

    @PostMapping("/handle")
    @Operation(summary = "处理申请")
    public R handle(@RequestBody @Valid ApplyHandleRequest request) {
        Long uid = RequestContext.getUid();
        applyService.handleApply(uid,request);
        return R.ok();
    }
}
