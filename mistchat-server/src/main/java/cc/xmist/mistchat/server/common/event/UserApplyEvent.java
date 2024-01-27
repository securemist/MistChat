package cc.xmist.mistchat.server.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserApplyEvent extends ApplicationEvent {
    private Long applyId;

    public UserApplyEvent(Object source, Long applyId) {
        super(source);
        this.applyId = applyId;
    }
}
