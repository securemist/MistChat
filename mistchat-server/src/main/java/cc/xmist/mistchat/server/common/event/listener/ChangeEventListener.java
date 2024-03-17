package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.MemberChangeEvent;
import org.springframework.context.event.EventListener;

public class ChangeEventListener {

    @EventListener(classes = MemberChangeEvent.class)
    public void pushMsg(MemberChangeEvent event) {

    }
}
