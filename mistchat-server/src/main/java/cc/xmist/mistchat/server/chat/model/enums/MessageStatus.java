package cc.xmist.mistchat.server.chat.model.enums;

import cc.xmist.mistchat.server.common.config.convert.BaseEnum;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatus implements BaseEnum {
    NORMAL(1),
    DELETE(0);

    private Integer code;
}
