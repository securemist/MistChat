package cc.xmist.mistchat.server.user;

import cc.xmist.mistchat.server.user.entity.ItemConfig;
import cc.xmist.mistchat.server.user.entity.UserBackpack;
import cc.xmist.mistchat.server.user.model.resp.BadgesResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {
    public static BadgesResponse buildBadgeResponse(Long uid, Long usedItemId, List<ItemConfig> allBadges, List<UserBackpack> userBadges) {
        BadgesResponse badgesResponse = new BadgesResponse();
        badgesResponse.setUid(uid);
        badgesResponse.setUsedBadgeId(usedItemId);

        // 用户徽章id列表
        List<Long> userBadgeIds = userBadges
                .stream()
                .map(userBackpack -> userBackpack.getItemId())
                .collect(Collectors.toList());

        List<BadgesResponse.Badge> badges = allBadges
                .stream()
                .map(badge -> {
                    boolean own = userBadgeIds.contains(badge.getId());
                    return BadgesResponse.Badge.builder()
                            .own(own)
                            .id(badge.getId())
                            .img(badge.getImg())
                            .description(badge.getDescription())
                            .build();
                }).collect(Collectors.toList());

        badgesResponse.setBadges(badges);
        return badgesResponse;
    }
}
