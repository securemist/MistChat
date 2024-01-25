package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.user.dao.BlackDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.model.IpInfo;
import cc.xmist.mistchat.server.user.model.enums.BlackType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BlackService {
    @Resource
    private BlackDao blackDao;
    @Resource
    private UserDao userDao;

    /**
     * 拉黑用户
     *
     * @param uid  用户id
     * @param type 类型
     */
    public void block(Long uid, Integer type) {
        BlackType blackType = BlackType.of(type);

        String target;
        if (blackType.equals(BlackType.UID)) {
            target = uid.toString();
        } else {
            IpInfo ipInfo = userDao.getUser(uid).getIpInfo();
            if (ipInfo == null) return; // 没有ip记录
            target = ipInfo.getLastIp();
        }
        blackDao.addBlack(uid, blackType, target);
    }
}
