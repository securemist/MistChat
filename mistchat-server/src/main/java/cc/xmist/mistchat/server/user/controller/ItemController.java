package cc.xmist.mistchat.server.user.controller;

import cc.xmist.mistchat.server.common.context.RequestContext;
import cc.xmist.mistchat.server.common.util.R;
import cc.xmist.mistchat.server.user.model.resp.BadgeVo;
import cc.xmist.mistchat.server.user.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "徽章接口")
@RequestMapping("/user")
public class ItemController {
    @Resource
    private ItemService badgeService;

    @Operation(summary = "获取用户徽章列表")
    @GetMapping("/badges")
    public R<List<BadgeVo>> getBadges() {
        Long uid = RequestContext.getUid();
        return R.ok(badgeService.getBadgeList(uid));
    }

    @Operation(summary = "佩戴徽章")
    @PostMapping("/badge/wear/{badgeId}")
    public R wearBadge(@PathVariable Long badgeId) {
        Long uid = RequestContext.getUid();
        badgeService.wearBadge(uid, badgeId);
        return R.ok();
    }
}
