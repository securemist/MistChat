package cc.xmist.mistchat.server.common.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements BaseEnum {

    NORMAL(1),
    BLACK(0);

    @EnumValue
    @JsonValue
    private Integer code;
}