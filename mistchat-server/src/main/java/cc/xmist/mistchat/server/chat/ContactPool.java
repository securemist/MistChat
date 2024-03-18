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
    private Map<Long, Long> openedConcat = new ConcurrentHashMap<>();

    public void openFriend(Long uid, Long friendId) {

    }

    public void closeFriend() {

    }

    public void openGroup(Long uid, Long contactId) {

    }

    public void closeGroup() {

    }

    public void remove() {

    }

}
