package cc.xmist.mistchat.server.user.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物品类型
 */
@AllArgsConstructor
@Getter
public enum Item implements BaseEnum {
    MODIFY_NAME_CARD(1, Type.MODIFY_NAME_CARD),
    LIKE_BADGE(2, Type.BADGE),
    REG_TOP10_BADGE(3, Type.BADGE),
    REG_TOP100_BADGE(4, Type.BADGE),
    PLANET(5, Type.BADGE),
    CONTRIBUTOR(6, Type.BADGE),
    ;

    @JsonValue
    @EnumValue
    public Integer code;
    public Type type;

    @Getter
    @AllArgsConstructor
    public static enum Type implements BaseEnum{
        MODIFY_NAME_CARD(0),
        BADGE(1);

        @JsonValue
        @EnumValue
        public Integer code;
    }

    // 所有的徽章id
    private static List<Integer> BadgeItemIds = Arrays.stream(Item.values())
            .filter(item -> item.getType().equals(Type.BADGE))
            .map(Item::getCode)
            .collect(Collectors.toList());

    /**
     * 获取所有徽章物品id
     *
     * @return
     */
    public static List<Integer> getBadgesId() {
        return BadgeItemIds;
    }
}