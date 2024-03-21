package cc.xmist.mistchat.server.friend.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.friend.req.FriendApplyHandleRequest;
import cc.xmist.mistchat.server.friend.req.FriendApplyRequest;
import cc.xmist.mistchat.server.friend.resp.FriendApplyHandleResponse;
import cc.xmist.mistchat.server.friend.resp.FriendApplyResponse;
import cc.xmist.mistchat.server.friend.service.FriendService;
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
    public R<Void> add(@RequestBody @Valid FriendApplyRequest req ) {
        Long uid = RequestContext.getUid();
        return R.ok(friendService.apply(uid, req));
    }

    @GetMapping("/apply/list")
    @Operation(summary = "获取收到的好友申请")
    public R<FriendApplyResponse> listApply() {
        Long uid = RequestContext.getUid();
        FriendApplyResponse response = friendService.getApplyList(uid);
        return R.ok(response);
    }

    @PostMapping("/apply/handle")
    @Operation(summary = "处理申请")
    public R<FriendApplyHandleResponse> handle(@RequestBody @Valid FriendApplyHandleRequest req) {
        Long uid = RequestContext.getUid();
        FriendApplyHandleResponse response = friendService.handleApply(uid, req);
        return R.ok(response);
    }

    @GetMapping("/list")
    @Operation(summary = "获取好友列表")
    public R<List<Long>> getFriendList() {
        Long uid = RequestContext.getUid();
        return R.ok(friendService.getFriendIdList(uid));
    }
}
