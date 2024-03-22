package cc.xmist.mistchat.server.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserOfflineEvent extends ApplicationEvent {
    private Integer uid;
    public UserOfflineEvent(Object source,Integer uid) {
        super(source);
        this.uid = uid;
    }
}
