package cc.xmist.mistchat.server.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户在线状态
 */
@Getter
@AllArgsConstructor
public enum ActiveType {
    ON(1),
    OFF(0);
    private Integer key;
}
