package cc.xmist.mistchat.server.websocket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum WSRequestType {

    LOGIN(1, "请求登陆二维码"),
    HEARTBEAT(2, "心跳"),
    AUTHORIZE(3, "登陆认证"),
    ;

    Integer type;
    String desc;

    private static Map<Integer, WSRequestType> map;

    static {
        map = Arrays.stream(WSRequestType.values()).collect(Collectors.toMap(WSRequestType::getType, Function.identity()));
    }

    public static WSRequestType of(Integer type) {
        return map.get(type);
    }
}
