package cc.xmist.mistchat.server.common.event;

import org.springframework.context.ApplicationEvent;

public class MemberChangeEvent extends ApplicationEvent {
    private Integer uid;
    private String  groupId;
    private boolean isExit;

    public MemberChangeEvent(Object source, Integer uid, String  groupId, boolean isExit) {
        super(source);
        this.uid = uid;
        this.groupId= groupId;
        this.isExit = isExit;
    }
}
