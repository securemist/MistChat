package cc.xmist.mistchat.server.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserOfflineEvent extends ApplicationEvent {
    private Long uid;
    public UserOfflineEvent(Object source,Long uid) {
        super(source);
        this.uid = uid;
    }
}
