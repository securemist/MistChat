package cc.xmist.mistchat.server.group.controller;

import cc.xmist.mistchat.server.common.constant.Constants;
import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.group.entity.Group;
import cc.xmist.mistchat.server.group.req.GroupCreateRequest;
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
    public R<CursorResult<Integer>> members(@RequestParam String groupId,
                                         @RequestParam(required = false) String cursor,
                                         @RequestParam(required = false,defaultValue = Constants.CUSROR_PAGESIZE) Integer pageSize) {
        CursorResult<Integer> result = groupService.getMembersCursorabler(groupId, cursor, pageSize);
        return R.ok(result);
    }

    @PostMapping("/create")
    @Operation(summary = "创建群聊")
    public R<Group> create(@RequestBody GroupCreateRequest req) {
        Integer uid = RequestContext.getUid();
        Group group = groupService.create(uid, req.getName(), req.getUidList());
        return R.ok(group);
    }

    @GetMapping("/exit")
    @Operation(summary = "退出群聊")
    public R exit(String groupId) {
        Integer uid = RequestContext.getUid();
        groupMemberService.exit(uid, groupId);
        return R.ok();
    }
}
