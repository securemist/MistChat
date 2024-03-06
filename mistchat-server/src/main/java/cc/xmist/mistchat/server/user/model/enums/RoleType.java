package cc.xmist.mistchat.server.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色类型
 */
@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN(0),
    USER(1),
    BOT(2);

    private Integer key;
}
