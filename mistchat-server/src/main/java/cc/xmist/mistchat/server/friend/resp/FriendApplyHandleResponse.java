package cc.xmist.mistchat.server.friend.resp;

import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.common.enums.ApplyStatus;
import cc.xmist.mistchat.server.friend.entity.FriendApply;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendApplyHandleResponse {
    private Integer applyId;
    private ApplyStatus status;
    private Contact contact;

    public static FriendApplyHandleResponse build(FriendApply apply, Contact contact) {
        return FriendApplyHandleResponse.builder()
                .applyId(apply.getId())
                .status(apply.getStatus())
                .contact(contact)
                .build();
    }
}