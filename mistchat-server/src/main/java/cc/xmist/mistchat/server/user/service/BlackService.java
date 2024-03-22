package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.enums.BlackType;
import cc.xmist.mistchat.server.user.dao.BlackDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.entity.IpInfo;
import cc.xmist.mistchat.server.user.model.entity.Black;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlackService {
    private final BlackDao blackDao;

    /**
     * 拉黑用户
     * @param uid
     * @param targetUid
     * @param blackType
     */
    public void block(Integer uid, Integer targetUid, BlackType blackType) {
        String target = switch (blackType) {
            case UID -> targetUid.toString();
        };
        blackDao.addBlack(uid, blackType, target);
    }
}
