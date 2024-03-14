package cc.xmist.mistchat.server.user.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 黑名单类型
 */
@Getter
@AllArgsConstructor
public enum BlackType implements BaseEnum {
    IP(0),
    UID(1);
    @JsonValue
    @EnumValue
    public Integer code;
}
