package cc.xmist.mistchat.server.user.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

/**
 * 幂等类型
 */
@AllArgsConstructor
public enum IdempotentType {
    UID(1),
    MSG_ID(2);
    @JsonValue
    @EnumValue
    public Integer code;
}
