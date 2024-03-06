package cc.xmist.mistchat.server.user.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 */
@Getter
@AllArgsConstructor
public enum SexType {
    M(1),
    W(0);

    @EnumValue
    private Integer key;
}
