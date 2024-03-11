package cc.xmist.mistchat.server.common.event;

import cc.xmist.mistchat.server.user.model.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisterEvent extends ApplicationEvent {
    private User user;

    public UserRegisterEvent(Object source,User user) {
        super(source);
        this.user = user;
    }
}
