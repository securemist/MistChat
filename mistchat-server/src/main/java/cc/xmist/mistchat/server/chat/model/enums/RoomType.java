package cc.xmist.mistchat.server.chat.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType implements BaseEnum {
    FRIEND(1),
    GROUP(2);

    private Integer code;
}
