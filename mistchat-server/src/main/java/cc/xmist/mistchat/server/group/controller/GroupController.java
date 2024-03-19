package cc.xmist.mistchat.server.group.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.group.req.GroupCreateReq;
import cc.xmist.mistchat.server.group.sevrice.GroupMemberService;
import cc.xmist.mistchat.server.group.sevrice.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@Tag(name = "群聊")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;

    @GetMapping("/members")
    @Operation(summary = "获取群聊的成员")
    public R<CursorResult<Long>> members(@RequestParam Long groupId,
                                         @RequestParam(required = false) String cursor,
                                         @RequestParam Integer pageSize) {
        CursorResult<Long> result = groupService.getMembersCursorabler(groupId, cursor, pageSize);
        return R.ok(result);
    }

    @PostMapping("/create")
    @Operation(summary = "创建群聊")
    public R<Long> create(@RequestBody GroupCreateReq req) {
        Long uid = RequestContext.getUid();
        Long groupId = groupService.create(uid, req.getName(), req.getUidList());
        return R.ok(groupId);
    }

    @GetMapping("/exit")
    @Operation(summary = "退出群聊")
    public R exit(Long groupId) {
        Long uid = RequestContext.getUid();
        groupMemberService.exit(uid, groupId);
        return R.ok();
    }
}
