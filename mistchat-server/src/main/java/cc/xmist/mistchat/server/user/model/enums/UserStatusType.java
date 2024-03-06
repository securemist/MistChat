package cc.xmist.mistchat.server.user.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusType {
    NORMAL(1),
    BLACK(0);
    @EnumValue
    private Integer key;
}
