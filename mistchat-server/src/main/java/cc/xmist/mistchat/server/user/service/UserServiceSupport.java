package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.user.dao.ItemConfigDao;
import cc.xmist.mistchat.server.user.dao.UserBackpackDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import jakarta.annotation.Resource;

public class UserServiceSupport {
    @Resource
    protected UserBackpackDao userBackpackDao;
    @Resource
    protected ItemConfigDao itemConfigDao;
    @Resource
    protected UserDao userDao;
}
