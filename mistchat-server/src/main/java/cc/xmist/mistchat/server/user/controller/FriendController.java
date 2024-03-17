package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.req.FriendApplyHandleReq;
import cc.xmist.mistchat.server.user.model.req.FriendApplyReq;
import cc.xmist.mistchat.server.user.model.resp.ForwardApplyVo;
import cc.xmist.mistchat.server.user.model.resp.ReceivedApplyVo;
import cc.xmist.mistchat.server.user.model.resp.SummaryUser;
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
    private FriendService friendService;

    @PostMapping("/apply/add")
    @Operation(summary = "添加好友申请")
    public R<Void> add(@RequestBody @Valid FriendApplyReq req, Long uid) {
        return R.ok(friendService.apply(uid, req));
    }

    @GetMapping("/rApply/list")
    @Operation(summary = "获取收到的好友申请")
    public R<List<ReceivedApplyVo>> getReceivedApplyList(Long uid) {
        List<ReceivedApplyVo> applyList = friendService.getReceivedApplyList(uid);
        return R.ok(applyList);
    }

    @GetMapping("/fApply/list")
    @Operation(summary = "获取发送出好友申请")
    public R<List<ForwardApplyVo>> getForwardApplyList(Long uid) {
        List<ForwardApplyVo> applyList = friendService.getForwardApplyList(uid);
        return R.ok(applyList);
    }

    @PostMapping("/apply/handle")
    @Operation(summary = "处理申请")
    public R<Void> handle(@RequestBody @Valid FriendApplyHandleReq req, Long uid) {
        friendService.handleApply(uid, req);
        return R.ok();
    }

    @GetMapping("/list")
    @Operation(summary = "获取好友列表")
    public R<List<SummaryUser>> getFriendList(Long uid) {
        return R.ok(friendService.getFriendIdList(uid));
    }
}
