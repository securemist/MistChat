package cc.xmist.mistchat.server.common.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyType implements BaseEnum {
    FRIEND(1),
    GROUP(2);

    @JsonValue
    @EnumValue
    private Integer code;
}
