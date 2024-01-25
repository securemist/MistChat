package cc.xmist.mistchat.server.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserOnlineEvent extends ApplicationEvent {

    private Long uid;
    private String ip;

    public UserOnlineEvent(Object source, Long uid, String ip) {
        super(source);
        this.uid = uid;
        this.ip = ip;
    }
}
