package cc.xmist.mistchat.server.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FriendApplyEvent extends ApplicationEvent {
    private Long applyId;

    public FriendApplyEvent(Object source, Long applyId) {
        super(source);
        this.applyId = applyId;
    }
}
