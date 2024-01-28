package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.user.dao.UserFriendDao;
import cc.xmist.mistchat.server.user.model.entity.UserFriend;
import cc.xmist.mistchat.server.user.model.vo.SummaryUser;
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
    public List<Long> getFriendIdList(Long uid) {
        List<UserFriend> friendList = userFriendDao.getFriendList(uid);
        return friendList.stream()
                .map(UserFriend::getFriendUid)
                .collect(Collectors.toList());
    }

    public List<SummaryUser> getFriendInfoList(Long uid) {
        List<Long> friendIdList = getFriendIdList(uid);
        return userService.getSummaryUsers(friendIdList);
    }
}
