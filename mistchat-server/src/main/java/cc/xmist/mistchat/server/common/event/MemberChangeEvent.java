package cc.xmist.mistchat.server.common.event;

import org.springframework.context.ApplicationEvent;

public class MemberChangeEvent extends ApplicationEvent {
    private Long uid;
    private Long groupId;
    private boolean isExit;

    public MemberChangeEvent(Object source, Long uid, Long groupId, boolean isExit) {
        super(source);
        this.uid = uid;
        this.groupId = groupId;
        this.isExit = isExit;
    }
}
