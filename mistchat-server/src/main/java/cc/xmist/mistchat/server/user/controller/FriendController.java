package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.req.ApplyAddRequest;
import cc.xmist.mistchat.server.user.model.req.ApplyHandleRequest;
import cc.xmist.mistchat.server.user.model.vo.SummaryUser;
import cc.xmist.mistchat.server.user.model.vo.FriendApplyVo;
import cc.xmist.mistchat.server.user.service.ApplyService;
import cc.xmist.mistchat.server.user.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/friend")
@Tag(name = "好友接口")
public class FriendController {
    @Resource
    private ApplyService applyService;
    @Resource
    private FriendService friendService;

    @PostMapping("/apply/add")
    @Operation(summary = "添加好友申请")
    public R<Void> add(@RequestBody @Valid ApplyAddRequest request) {
        Long uid = RequestContext.getUid();
        applyService.createApply(uid, request);
        return R.ok();
    }

    @GetMapping("/apply/list")
    @Operation(summary = "获取好友申请列表")
    public R<List<FriendApplyVo>> getApplyList() {
        Long uid = RequestContext.getUid();
        List<FriendApplyVo> applyList = applyService.getApplyList(uid);
        return R.ok(applyList);
    }

    @PostMapping("/apply/handle")
    @Operation(summary = "处理申请")
    public R<Void> handle(@RequestBody @Valid ApplyHandleRequest request) {
        Long uid = RequestContext.getUid();
        applyService.handleApply(uid, request);
        return R.ok();
    }

    @GetMapping("/list")
    @Operation(summary = "获取好友列表")
    public R<List<SummaryUser>> getFriendList() {
        Long uid = RequestContext.getUid();
        List<SummaryUser> friendInfoList = friendService.getFriendInfoList(uid);
        return R.ok(friendInfoList);
    }


}
