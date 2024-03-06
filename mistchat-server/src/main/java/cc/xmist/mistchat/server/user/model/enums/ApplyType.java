package cc.xmist.mistchat.server.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyType {
    FRIEND(0),
    GROUP(1);

    private Integer key;
}
