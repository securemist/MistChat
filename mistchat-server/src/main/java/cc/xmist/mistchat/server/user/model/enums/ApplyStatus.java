package cc.xmist.mistchat.server.user.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyStatus implements BaseEnum {
    WAIT(0),
    PASS(1),
    FORBID(2),
    READ(3);

    @JsonValue
    @EnumValue
    private Integer code;
}
