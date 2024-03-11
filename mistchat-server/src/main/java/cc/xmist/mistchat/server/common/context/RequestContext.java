package cc.xmist.mistchat.server.common.context;

import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.model.entity.User;
import cn.hutool.extra.spring.SpringUtil;

/**
 * 请求上下文
 * 存储请求的用户uid信息和ip
 * 建议只在controller中使用
 */
public class RequestContext {
    private static ThreadLocal<Long> uidLocal = new ThreadLocal<>();

    private static UserDao userDao;

    static {
        userDao = SpringUtil.getBean(UserDao.class);
    }

    public static Long getUid() {
        return uidLocal.get();
    }

    public static void setUid(Long uid) {
        uidLocal.set(uid);
    }

    public static User getUser() {
        return userDao.getUser(uidLocal.get());
    }

    public static void remove() {
        uidLocal.remove();
    }
}
