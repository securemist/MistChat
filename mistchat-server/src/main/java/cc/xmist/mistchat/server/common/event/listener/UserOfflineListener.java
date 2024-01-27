package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.UserOfflineEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserOfflineListener {
    @EventListener(UserOfflineEvent.class)
    public void offline(UserOfflineEvent event) {

    }
}
