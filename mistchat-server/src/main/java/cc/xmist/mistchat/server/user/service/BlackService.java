package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.enums.BlackType;
import cc.xmist.mistchat.server.user.dao.BlackDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.entity.IpInfo;
import cc.xmist.mistchat.server.user.model.entity.Black;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void block(Long uid, BlackType blackType) {
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

    /**
     * 获取所有被拉黑的uid
     *
     * @return
     */
    public List<Long> getBlockedUid() {
        List<Black> blacks = blackDao.getBlacks(BlackType.UID);
        return blacks.stream()
                .map(Black::getTarget)
                .map(ip -> Long.valueOf(ip))
                .collect(Collectors.toList());
    }

    /**
     * 获取所有被拉黑的ip
     *
     * @return
     */
    public List<String> getBlockedIp() {
        List<Black> blacks = blackDao.getBlacks(BlackType.IP);
        return blacks.stream()
                .map(Black::getTarget)
                .collect(Collectors.toList());
    }
}
