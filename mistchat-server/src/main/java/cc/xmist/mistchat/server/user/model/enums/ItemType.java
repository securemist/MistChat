package cc.xmist.mistchat.server.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 物品类型
 */
@AllArgsConstructor
@Getter
public enum ItemType {
    MODIFY_NAME_CARD(1L, Type.MODIFY_NAME_CARD),
    LIKE_BADGE(2L, Type.BADGE),
    REG_TOP10_BADGE(3L, Type.BADGE),
    REG_TOP100_BADGE(4L, Type.BADGE),
    PLANET(5L, Type.BADGE),
    CONTRIBUTOR(6L, Type.BADGE),
    ;

    public Long id;
    public Type type;

    public static enum Type {
        MODIFY_NAME_CARD,
        BADGE;
    }

    // 所有的徽章id
    public static List<Long> BadgeItemIds = Arrays.stream(ItemType.values())
            .filter(item -> item.getType().equals(Type.BADGE))
            .map(ItemType::getId)
            .collect(Collectors.toList());

    /**
     * 获取所有徽章物品id
     *
     * @return
     */
    public static List<Long> getBadgesId() {
        return BadgeItemIds;
    }
}