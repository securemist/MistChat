package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.user.dao.UserFriendDao;
import cc.xmist.mistchat.server.user.model.entity.UserFriend;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendService {
    @Resource
    private UserFriendDao userFriendDao;
    @Resource
    private UserService userService;


    /**
     * 获取好友列表
     *
     * @param uid
     * @return
     */
    public List<Long> getFriendIdList(Long uid) {
        List<UserFriend> friendList = userFriendDao.getFriendList(uid);
        return friendList.stream()
                .map(UserFriend::getFriendUid)
                .collect(Collectors.toList());
    }
}
