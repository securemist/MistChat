package cc.xmist.mistchat.server.user;

import cc.xmist.mistchat.server.user.entity.ItemConfig;
import cc.xmist.mistchat.server.user.entity.UserBackpack;
import cc.xmist.mistchat.server.user.model.vo.BadgeVo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {
    public static List<BadgeVo> buildBadgeResponse(Long uid, Long usedItemId, List<ItemConfig> allBadges, List<UserBackpack> userBadges) {

        // 用户徽章id列表
        List<Long> userBadgeIds = userBadges
                .stream()
                .map(userBackpack -> userBackpack.getItemId())
                .collect(Collectors.toList());

        /**
         * 排序逻辑，佩戴的 > 拥有的 > 未拥有的 > id 顺序
         */
        List<BadgeVo> badges = allBadges
                .stream()
                .map(badge -> {
                    boolean own = userBadgeIds.contains(badge.getId());
                    return BadgeVo.builder()
                            .own(own)
                            .id(badge.getId())
                            .img(badge.getImg())
                            .description(badge.getDescription())
                            .wearing(usedItemId == badge.getId())
                            .build();
                })
                .sorted(Comparator.comparing(BadgeVo::getWearing, Comparator.reverseOrder())
                        .thenComparing(BadgeVo::getOwn, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return badges;
    }
}
