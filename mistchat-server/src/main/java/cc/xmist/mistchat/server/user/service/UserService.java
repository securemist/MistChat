package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.exception.BusinessException;
import cc.xmist.mistchat.server.common.exception.ParamException;
import cc.xmist.mistchat.server.common.util.JwtUtil;
import cc.xmist.mistchat.server.user.UserAdapter;
import cc.xmist.mistchat.server.user.dao.ItemConfigDao;
import cc.xmist.mistchat.server.user.dao.UserBackpackDao;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.entity.ItemConfig;
import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.entity.UserBackpack;
import cc.xmist.mistchat.server.user.model.enums.ItemType;
import cc.xmist.mistchat.server.user.model.resp.BadgeVo;
import cc.xmist.mistchat.server.user.model.resp.UserInfoResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private AuthService authService;
    @Resource
    private UserBackpackDao userBackpackDao;
    @Resource
    private ItemConfigDao itemConfigDao;


    public void register(String username, String password, String name) {
        User user = userDao.getByUsername(username);
        if (user != null) {
            throw new RuntimeException("该用户已注册");
        }

        userDao.addUser(username, password, name);
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

    public UserInfoResponse getUserInfo(Long uid) {
        User user = userDao.getUser(uid);
        Long modifyNameCount = userBackpackDao.getItemCount(uid, ItemType.MODIFY_NAME_CARD);

        return UserInfoResponse.builder()
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
     * 获取徽章列表
     *
     * @param uid
     * @return
     */
    public List<BadgeVo> getBadgeList(Long uid) {
        // 所有徽章物品id
        List<Long> badgeIdList = ItemType.getBadgeItemIdList();
        // 徽章详细信息
        List<ItemConfig> allBadges = itemConfigDao.getBadges(badgeIdList);
        // 用户徽章列表
        List<UserBackpack> userBadges = userBackpackDao.getItemList(uid, badgeIdList);

        // 用户已经佩戴了的徽章id
        User user = userDao.getUser(uid);
        Long usedItemId = user.getItemId();

        return UserAdapter.buildBadgeResponse(uid, usedItemId, allBadges, userBadges);
    }

}
