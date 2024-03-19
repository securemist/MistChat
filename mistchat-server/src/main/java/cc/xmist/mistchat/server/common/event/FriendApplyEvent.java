package cc.xmist.mistchat.server.common.event;

import cc.xmist.mistchat.server.friend.entity.FriendApply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FriendApplyEvent extends ApplicationEvent {
    private FriendApply apply;

    public FriendApplyEvent(Object source, FriendApply apply) {
        super(source);
        this.apply = apply;
    }
}
