package cc.xmist.mistchat.server.chat;

import cc.xmist.mistchat.server.friend.entity.Friend;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录每个用户在前端页面打开的会话
 * 当用户打开这个会话时，这个会话相关的时间才会触发
 */
@Component
public class ContactPool {
    private Map<Long, Long> friendConcat = new ConcurrentHashMap<>();
    private Map<Long, Long> groupConcat = new ConcurrentHashMap<>();

    public void openFriend(Long uid, Long friendId) {
        closeGroup(uid);
        friendConcat.put(uid, friendId);
    }

    public void closeFriend(Long uid) {
        friendConcat.remove(uid);
    }

    public void openGroup(Long uid, Long groupId) {
        closeFriend(uid);
        groupConcat.put(uid,groupId);
    }

    public void closeGroup(Long uid) {
        groupConcat.remove(uid);
    }

    public void remove(Long uid) {
        closeFriend(uid);
        closeGroup(uid);
    }
}
