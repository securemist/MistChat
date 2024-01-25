package cc.xmist.mistchat.server.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 黑名单类型
 */
@Getter
@AllArgsConstructor
public enum BlackType {
    IP(0),
    UID(1);
    public Integer key;

    private static Map<Integer, BlackType> map = Arrays
            .stream(BlackType.values())
            .collect(Collectors.toMap(BlackType::getKey, Function.identity()));

    public static BlackType of(Integer key) {
        return map.get(key);
    }
}
