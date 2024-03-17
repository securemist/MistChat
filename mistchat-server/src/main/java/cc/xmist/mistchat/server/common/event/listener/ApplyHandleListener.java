package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.FriendApplyEvent;
import cc.xmist.mistchat.server.friend.dao.FriendApplyDao;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplyHandleListener {
    @Resource
    private FriendApplyDao friendApplyDao;

    @EventListener(FriendApplyEvent.class)
    public void sendMsg(FriendApplyEvent event) {
//        UserApply apply = userApplyDao.getById(event.getApplyId());
//        ApplyType type = apply.getType();
//        ApplyStatus status = apply.getStatus();

//        switch (type) {
//            case FRIEND -> {
//                    if (status.equals(ApplyStatus.PASS)) {
//                    // TODO
//                }
//            }
//        }
    }
}
