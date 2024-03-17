package cc.xmist.mistchat.server.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户在线状态
 */
@Getter
@AllArgsConstructor
public enum ActiveType {
    ON(1),
    OFF(0);
    @EnumValue
    @JsonValue
    private Integer code;
}
