package cc.xmist.mistchat.server.chat.model.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Groups {
    ALL_USERS_GROUP(1L);
    private Long id;
}
