package cc.xmist.mistchat.server.chat.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType implements BaseEnum {
    TEXT(1),
    IMG(2),
    FILE(3),
    VIDEO(4),
    SOUND(5),
    LINK(6),
    RECALL(7),
    SYSTEM(0); // 系统消息

    @EnumValue
    @JsonValue
    private Integer code;
}
