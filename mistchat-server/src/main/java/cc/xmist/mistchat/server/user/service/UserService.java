package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.event.UserRegisterEvent;
import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.entity.UserBackpack;
import cc.xmist.mistchat.server.user.model.IpInfo;
import cc.xmist.mistchat.server.user.model.enums.ItemType;
import cc.xmist.mistchat.server.user.model.vo.UserInfoVo;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService extends UserServiceSupport {

    @Resource
    ApplicationEventPublisher eventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public void register(String username, String password, String name) {
        if (userDao.getByUsername(username) != null) {
            throw new BusinessException("该用户已注册");
        }

        if (userDao.getByName(name) != null) {
            throw new BusinessException("该用户名已存在");
        }

        User user = userDao.addUser(username, password, name);
        eventPublisher.publishEvent(new UserRegisterEvent(this, user));
        log.info("{}用户完成注册，username：{}", name, username);
    }

    public User login(String username, String password) {
        User user = userDao.getByUsername(username);
        if (user == null) {
            throw new BusinessException("用户名不存在");
        }

        if (!user.getPassword().equals(password)) {
            throw new BusinessException("密码错误");
        }

        String token = authService.login(user.getId());
        log.info("用户 {} 登陆成功", user.getName());
        return user;
    }

    public UserInfoVo getUserInfo(Long uid) {
        User user = userDao.getUser(uid);
        Long modifyNameCount = userBackpackDao.getItemCount(uid, ItemType.MODIFY_NAME_CARD);

        return UserInfoVo.builder()
                .id(user.getId())
                .sex(user.getSex())
                .avatar(user.getAvatar())
                .name(user.getName())
                .modifyNameChance(modifyNameCount).build();
    }


    /**
     * 修改用户用户名
     *
     * @param uid
     * @param name 新用户名
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyName(Long uid, String name) {
        // uid不存在
        if (userDao.getUser(uid) == null) {
            throw new ParamException();
        }

        // 校验用户名
        User user = userDao.getByName(name);
        if (user != null) {
            throw new BusinessException("该用户名已存在");
        }

        // 获取用户最新的一张改名卡
        UserBackpack renameItem = userBackpackDao.getLastItem(uid, ItemType.MODIFY_NAME_CARD);
        if (renameItem == null) {
            throw new BusinessException("改名卡数量不足");
        }

        // 消耗改名卡改名
        userBackpackDao.useItem(renameItem.getId());
        userDao.modifyName(uid, name);
    }


    /**
     * 更新ip信息
     *
     * @param uid
     * @param ip
     */
    public void updateIpInfo(Long uid, String ip) {
        User user = userDao.getUser(uid);
        IpInfo ipInfo = user.getIpInfo();
        if (StrUtil.isBlank(ipInfo.getInitialIp())) {
            ipInfo.setInitialIp(ip);
            ipInfo.setLastIp(ip);
        } else {
            ipInfo.setLastIp(ip);
        }
    }
}
