package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.UserOnlineEvent;
import cc.xmist.mistchat.server.socketio.SocketService;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.service.IpService;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserOnlineListener {
    @Resource
    private UserDao userDao;
    @Resource
    private IpService ipService;
    @Resource
    private SocketService socketService;


    @EventListener(classes = UserOnlineEvent.class)
    public void online(UserOnlineEvent event){
        Long uid = event.getUid();
        userDao.online(uid);
    }

    @EventListener(classes = UserOnlineEvent.class)
    public void parseIpInfo(UserOnlineEvent event) {
        Long uid = event.getUid();
        ipService.updateIpInfo(uid, event.getIp());
    }
}
