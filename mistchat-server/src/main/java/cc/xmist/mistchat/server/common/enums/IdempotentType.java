package cc.xmist.mistchat.server.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 幂等类型
 */
@AllArgsConstructor
@Getter
public enum IdempotentType {
    UID(1),
    MSG_ID(2);
    @JsonValue
    @EnumValue
    public Integer code;
}
