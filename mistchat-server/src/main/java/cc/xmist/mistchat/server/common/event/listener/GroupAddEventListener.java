package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.GroupAddEvent;
import cc.xmist.mistchat.server.group.entity.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GroupAddEventListener {

    @EventListener(GroupAddEvent.class)
    public void pushMsg(GroupAddEvent event) {
        Group group = event.getGroup();
        List<Long> list = event.getMembers();

        // TODO
    }
}
