package cc.xmist.mistchat.server.common.event;

import cc.xmist.mistchat.server.group.entity.Group;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class GroupAddEvent extends ApplicationEvent {
    private Group group;
    private List<Integer> members;

    public GroupAddEvent(Object source, Group group, List<Integer> members) {
        super(source);
        this.group = group;
        this.members = members;
    }
}
