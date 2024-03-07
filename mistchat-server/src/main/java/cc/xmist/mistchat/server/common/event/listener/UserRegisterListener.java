package cc.xmist.mistchat.server.common.event.listener;

import cc.xmist.mistchat.server.common.event.UserRegisterEvent;
import cc.xmist.mistchat.server.user.dao.UserDao;
import cc.xmist.mistchat.server.user.model.entity.User;
import cc.xmist.mistchat.server.user.model.enums.IdempotentType;
import cc.xmist.mistchat.server.user.model.enums.Item;
import cc.xmist.mistchat.server.user.service.ItemService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserRegisterListener {
    @Resource
    ItemService itemService;
    @Resource
    UserDao userDao;

    /**
     * 用户注册后发送物品
     * 改名卡 + 前10/100名徽章
     * @param event
     */
    @TransactionalEventListener(classes = UserRegisterEvent.class)
    @Async
    public void sendItem(UserRegisterEvent event) {
        // 用户注册发送改名卡
        User user = event.getUser();
        long userCount = userDao.count();
        Item badge = null;

        if (userCount < 12L) {
            badge = Item.REG_TOP10_BADGE;
        } else if (userCount < 102L) {
            badge = Item.REG_TOP100_BADGE;
        }

        if (badge != null) {
            itemService.acquireItem(user.getId(), badge, IdempotentType.UID, user.getId().toString());
        }
        itemService.acquireItem(user.getId(), Item.MODIFY_NAME_CARD, IdempotentType.UID, user.getId().toString());

    }


}
