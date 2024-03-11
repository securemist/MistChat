package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.req.ApplyAddReq;
import cc.xmist.mistchat.server.user.model.req.ApplyHandleReq;
import cc.xmist.mistchat.server.user.model.vo.ForwardApplyVo;
import cc.xmist.mistchat.server.user.model.vo.ReceivedApplyVo;
import cc.xmist.mistchat.server.user.model.vo.SummaryUser;
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
    public R<Void> add(@RequestBody @Valid ApplyAddReq request, Long uid) {
        applyService.createApply(uid, request);
        return R.ok();
    }

    @GetMapping("/rApply/list")
    @Operation(summary = "获取收到的好友申请")
    public R<List<ReceivedApplyVo>> getReceivedApplyList(Long uid) {
        List<ReceivedApplyVo> applyList = applyService.getReceivedApplyList(uid);
        return R.ok(applyList);
    }

    @GetMapping("/fApply/list")
    @Operation(summary = "获取发送出好友申请")
    public R<List<ForwardApplyVo>> getForwardApplyList(Long uid) {
        List<ForwardApplyVo> applyList = applyService.getForwardApplyList(uid);
        return R.ok(applyList);
    }

    @PostMapping("/apply/handle")
    @Operation(summary = "处理申请")
    public R<Void> handle(@RequestBody @Valid ApplyHandleReq request, Long uid) {
        applyService.handleApply(uid, request);
        return R.ok();
    }

    @GetMapping("/list")
    @Operation(summary = "获取好友列表")
    public R<List<SummaryUser>> getFriendList(Long uid) {
        List<Long> friendInfoList = friendService.getFriendIdList(uid);
        return R.ok(friendInfoList);
    }


}
