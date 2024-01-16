package cc.xmist.mistchat.server.user.model.enums;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum ItemType {
    MODIFY_NAME_CARD(1L, ItemTypeEnum.MODIFY_NAME_CARD, "改名卡"),
    LIKE_BADGE(2L, ItemTypeEnum.BADGE, "爆赞徽章"),
    REG_TOP10_BADGE(3L, ItemTypeEnum.BADGE, "前十注册徽章"),
    REG_TOP100_BADGE(4L, ItemTypeEnum.BADGE, "前100注册徽章"),
    PLANET(5L, ItemTypeEnum.BADGE, "知识星球"),
    CONTRIBUTOR(6L, ItemTypeEnum.BADGE, "代码贡献者"),
    ;

    final Long id;
    final ItemTypeEnum typeEnum;
    final String desc;

    private static Map<Long, ItemType> cache;

    static {
        cache = Arrays.stream(ItemType.values()).collect(Collectors.toMap(ItemType::getId, Function.identity()));
    }

    public static ItemType of(Long type) {
        return cache.get(type);
    }
}