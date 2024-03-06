package cc.xmist.mistchat.server.user.model.enums;

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

    private Integer key;
}
