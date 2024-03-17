package cc.xmist.mistchat.server.group.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.Cursor;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.group.model.req.GroupCreateReq;
import cc.xmist.mistchat.server.group.sevrice.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@Tag(name = "群聊")
public class GroupController {
    @Resource
    private GroupService groupService;

    @GetMapping("/members")
    @Operation(summary = "获取群聊的成员")
    public R members(Long groupId) {
        List<Long> list = groupService.getMembers(groupId);
        return R.ok(list);
    }

    @PostMapping("/create")
    @Operation(summary = "创建群聊")
    public R create(@RequestBody GroupCreateReq req) {
        Long uid = RequestContext.getUid();
        Long groupId = groupService.create(uid, req.getName(), req.getUidList());
        return R.ok(groupId);
    }
}
