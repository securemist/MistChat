package cc.xmist.mistchat.server.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyStatusType {
    WAIT(0),
    PASS(1),
    FORBID(2),
    READ(3);
    private Integer key;
}
