package cc.xmist.mistchat.server.user;

import cc.xmist.mistchat.server.user.model.entity.UserBackpack;
import cc.xmist.mistchat.server.common.enums.Item;
import cc.xmist.mistchat.server.user.model.resp.BadgeVo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserAdapter {
    public static List<BadgeVo> buildBadgeResponse(Long uid, Long usedItemId, List<Item> allBadges, List<UserBackpack> userBadges) {

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


//    public static FriendApplyVo.User buildUserInfo(User user) {
//        return FriendApplyVo.User
//                .builder()
//                .id(user.getId())
//                .name(user.getName())
//                .sex(user.getSex())
//                .avatar(user.getAvatar())
//                .role(user.getRole())
//                .build();
//    }
}
