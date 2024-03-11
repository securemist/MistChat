package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.req.UidListReq;
import cc.xmist.mistchat.server.user.model.vo.BadgeVo;
import cc.xmist.mistchat.server.user.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/badge")
@Tag(name = "徽章")
public class BadgeController {
    @Resource
    private ItemService itemService;

    @Operation(summary = "当前用户徽章列表")
    @GetMapping("/list")
    public R<List<BadgeVo>> getBadges(Long uid) {
        return R.ok(itemService.getBadgeList(uid));
    }

    @Operation(summary = "佩戴徽章")
    @PostMapping("wear/{badgeId}")
    public R wearBadge(@PathVariable Long badgeId,Long uid) {
        itemService.wearBadge(uid, badgeId);
        return R.ok();
    }

    @Operation(summary = "其他用户佩戴的徽章")
    @PostMapping("/another/list")
    public R getUserBadgeInfo(@RequestBody UidListReq req,Long uid) {
        return R.ok(itemService.getWaringBadgeInfo(req.getUidList()));
    }

}
