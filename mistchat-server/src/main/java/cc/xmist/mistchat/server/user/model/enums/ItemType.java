package cc.xmist.mistchat.server.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum ItemType {
    MODIFY_NAME_CARD(1L, ItemTypeEnum.MODIFY_NAME_CARD, "改名卡"),
    LIKE_BADGE(2L, ItemTypeEnum.BADGE, "爆赞徽章"),
    REG_TOP10_BADGE(3L, ItemTypeEnum.BADGE, "前10注册徽章"),
    REG_TOP100_BADGE(4L, ItemTypeEnum.BADGE, "前100注册徽章"),
    PLANET(5L, ItemTypeEnum.BADGE, "知识星球"),
    CONTRIBUTOR(6L, ItemTypeEnum.BADGE, "代码贡献者"),
    ;

    public Long id;
    public ItemTypeEnum typeEnum;
    public String desc;

    private static List<Long> BadgeItemIds = Arrays.stream(ItemType.values())
            .filter(item -> item.getTypeEnum().equals(ItemTypeEnum.BADGE))
            .map(ItemType::getId)
            .collect(Collectors.toList());

    /**
     * 获取所有徽章物品id
     * @return
     */
    public static List<Long> getBadgesId() {
        return BadgeItemIds;
    }
}