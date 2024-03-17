package cc.xmist.mistchat.server.group.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupStatus implements BaseEnum {
    OK(1,"正常"),
    ALL_FORBID(2,"全员禁言");


    @JsonValue
    @EnumValue
    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }
}
