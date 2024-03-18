package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.FriendContact;
import cc.xmist.mistchat.server.chat.mapper.FriendContactMapper;
import cc.xmist.mistchat.server.friend.dao.FriendDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FriendContactDao extends ServiceImpl<FriendContactMapper, FriendContact> {
    @Resource
    private FriendDao friendDao;

    public List<FriendContact> getByUid(Long uid) {
        return lambdaQuery()
                .eq(FriendContact::getUid, uid)
                .list();
    }

    /**
     * 两个用户成为好友，创建会话
     * @param uid1
     * @param uid2
     */
    public void firstCreate(Long uid1, Long uid2) {
        FriendContact f1 = FriendContact.builder()
                .friendUid(uid2)
                .uid(uid1)
                .build();

        FriendContact f2 = FriendContact.builder()
                .friendUid(uid1)
                .uid(uid2)
                .build();

        saveBatch(Arrays.asList(f1, f2));
    }

    /**
     * 更新活跃状态
     *
     * @param uid       发送消息的用户
     * @param friendUid 接受的好友 uid
     * @param msgId
     */
    public void updateActive(Long uid, Long friendUid, Long msgId) {
        lambdaUpdate()
                .set(FriendContact::getLastMsgId, msgId)
                .eq(FriendContact::getUid, uid)
                .eq(FriendContact::getFriendUid, friendUid)
                .update();
    }

    /**
     * 用户已读好友发送的消息
     * @param uid
     * @param friendUid
     * @param msgId
     */
    public void readMsg(Long uid, Long friendUid, Long msgId) {
        lambdaUpdate()
                .set(FriendContact::getReadMsgId, msgId)
                .eq(FriendContact::getUid, uid)
                .eq(FriendContact::getFriendUid, friendUid)
                .update();
    }
}
