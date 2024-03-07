package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.UserApplyEvent;
import cc.xmist.mistchat.server.user.dao.UserApplyDao;
import cc.xmist.mistchat.server.user.model.entity.UserApply;
import cc.xmist.mistchat.server.user.model.enums.ApplyStatus;
import cc.xmist.mistchat.server.user.model.enums.ApplyType;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplyHandleListener {
    @Resource
    private UserApplyDao userApplyDao;

    @EventListener(UserApplyEvent.class)
    public void sendMsg(UserApplyEvent event) {
        UserApply apply = userApplyDao.getById(event.getApplyId());
        ApplyType type = apply.getType();
        ApplyStatus status = apply.getStatus();

        switch (type) {
            case FRIEND -> {
                if (status.equals(ApplyStatus.PASS)) {

                }
            }
        }
    }
}
