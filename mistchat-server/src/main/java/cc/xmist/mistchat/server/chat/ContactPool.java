package cc.xmist.mistchat.server.chat;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录每个用户在前端页面打开的会话
 * 当用户打开这个会话时，这个会话相关的时间才会触发
 */
@Component
public class ContactPool {
    private Map<Integer, Integer> friendConcat = new ConcurrentHashMap<>();
    private Map<Integer, String> groupConcat = new ConcurrentHashMap<>();

    public void openFriend(Integer uid, Integer friendId) {
        closeGroup(uid);
        friendConcat.put(uid, friendId);
    }

    public void closeFriend(Integer uid) {
        friendConcat.remove(uid);
    }

    public void openGroup(Integer uid, String groupId) {
        closeFriend(uid);
        groupConcat.put(uid,groupId);
    }

    public void closeGroup(Integer uid) {
        groupConcat.remove(uid);
    }

    public void remove(Integer uid) {
        closeFriend(uid);
        closeGroup(uid);
    }
}
