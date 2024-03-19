package cc.xmist.mistchat.server.common.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemMsgType implements BaseEnum {

    BE_FRIEND(1, "你们已经成为好友了，开始聊天吧"),
    JOIN_GROUP(2,"您已加入群聊，打个招呼吧");

    @EnumValue
    @JsonValue
    private Integer code;
    private String msg;
}
