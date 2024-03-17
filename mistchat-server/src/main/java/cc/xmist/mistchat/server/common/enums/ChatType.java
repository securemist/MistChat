package cc.xmist.mistchat.server.common.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatType implements BaseEnum {
    FRIEND(1),
    GROUP(2);

    @EnumValue
    @JsonValue
    private Integer code;
}
