package cc.xmist.mistchat.server.user.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物品类型
 */
@AllArgsConstructor
@Getter
@ToString
public enum Item implements BaseEnum {
    MODIFY_NAME_CARD(1L, Type.MODIFY_NAME_CARD, null, "用户可以使用改名卡，更改自己的名字。mallchat名称全局唯一，快抢订你的专属昵称吧"),
    LIKE_BADGE(2L, Type.BADGE, "https://cdn-icons-png.flaticon.com/128/1533/1533913.png", "爆赞徽章，单条消息被点赞超过10次，即可获得爆赞徽章，单条消息被点赞超过10次，即可获得"),
    REG_TOP10_BADGE(3L, Type.BADGE, "https://cdn-icons-png.flaticon.com/512/6198/6198527.png ", "抹茶聊天前10名注册的用户才能获得的专属徽章"),
    REG_TOP100_BADGE(4L, Type.BADGE, "https://cdn-icons-png.flaticon.com/512/10232/10232583.png", "抹茶聊天前100名注册的用户才能获得的专属徽章"),
    PLANET(5L, Type.BADGE, "https://cdn-icons-png.flaticon.com/128/2909/2909937.png", "抹茶知识星球成员的专属徽章"),
    CONTRIBUTOR(6L, Type.BADGE, "https://minio.mallchat.cn/mallchat/%E8%B4%A1%E7%8C%AE%E8%80%85.png", "抹茶项目contributor专属徽章"),
    ;

    @JsonValue
    @EnumValue
    private Long id;
    private Type type;
    private String img;
    private String description;

    @Override
    public Integer getCode() {
        return Math.toIntExact(this.id);
    }

    @Getter
    @AllArgsConstructor
    public static enum Type implements BaseEnum {
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

    public static List<Item> all = Arrays.asList(Item.values());

    public static Item getById(Long id) {
        return  all.stream().filter(i -> i.id == id).findFirst().get();
    }

    /**
     * 获取所有徽章物品id
     *
     * @return
     */
    public static List<Integer> getBadgesId() {
        return BadgeItemIds;
    }
}