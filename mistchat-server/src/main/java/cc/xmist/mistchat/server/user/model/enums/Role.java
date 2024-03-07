package cc.xmist.mistchat.server.user.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role implements BaseEnum {
    ADMIN(0),
    USER(1),
    BOT(2);

    @EnumValue
    @JsonValue
    private Integer code;
}