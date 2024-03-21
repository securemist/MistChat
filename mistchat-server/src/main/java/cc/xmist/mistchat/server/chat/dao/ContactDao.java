package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.Contact;
import cc.xmist.mistchat.server.chat.mapper.ContactMapper;
import cc.xmist.mistchat.server.common.exception.IlleglaException;
import cc.xmist.mistchat.server.friend.entity.Friend;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {

    /**
     * 用户发送消息的会话更新
     *
     * @param contactId
     * @param msgId
     */
    public void updateSending(Long contactId, Long msgId) {
        lambdaUpdate()
                .set(Contact::getActiveMsgId, msgId)
                .set(Contact::getReadMsgId,msgId)
                .eq(Contact::getId, contactId)
                .update();
    }


    /**
     * 用户读取消息的会话更新
     * 某一个用户在某一个会话读取某个消息，如果会话的实际 uid 与请求的 uid 不符，属于非法操作，操作别人的接口
     *
     * @param uid    请求中的 uid
     * @param roomId
     * @param msgId  消息 id
     */
    public void updateReading(Long uid, Long roomId, Long msgId) {
        boolean ok = lambdaUpdate()
                .set(Contact::getReadMsgId, msgId)
                .eq(Contact::getUid, uid)
                .eq(Contact::getRoomId, roomId)
                .update();
        if (!ok) throw new IlleglaException();
    }


    /**
     * 列出用户所有的会话
     *
     * @param uid
     * @return
     */
    public List<Contact> listByUid(Long uid) {
        return lambdaQuery()
                .eq(Contact::getUid, uid)
                .list();
    }

    /**
     * 初始化好友会话
     *
     * @param friend
     */
    public void initFriend(Friend friend) {
        List<Contact> contacts = Arrays.asList(friend.getUid1(), friend.getUid2())
                .stream()
                .map(uid -> {
                    return Contact.builder()
                            .uid(uid)
                            .roomId(friend.getRoomId())
                            .build();
                }).collect(Collectors.toList());
        saveBatch(contacts);
    }

    /**
     * 初始化群成员会话
     *
     * @param groupId
     * @param uids
     */
    public void initGroup(Long groupId, List<Long> uids) {
        List<Contact> contacts = uids.stream().map(uid -> {
            return Contact.builder()
                    .uid(uid)
                    .roomId(groupId)
                    .build();
        }).collect(Collectors.toList());

        saveBatch(contacts);
    }

    /**
     * 一个聊天室内最新的消息 id
     *
     * @param roomId
     * @return
     */
    public Long getLastMsgId(Long roomId) {
        return lambdaQuery()
                .select(Contact::getActiveMsgId)
                .eq(Contact::getRoomId, roomId)
                .orderByDesc(Contact::getActiveMsgId)
                .last("LIMIT 1")
                .one()
                .getActiveMsgId();
    }

    public List<Contact> listCursorable(Long uid, String cursor, Integer pageSize) {
        return lambdaQuery()
                .lt(StrUtil.isNotBlank(cursor), Contact::getId, cursor)
                .eq(Contact::getUid, uid)
                .orderByDesc(Contact::getId)
                .last("LIMIT " + pageSize)
                .list();
    }

    public Contact getByRoomId(Long uid, Long roomId) {
        return lambdaQuery()
                .eq(Contact::getRoomId, roomId)
                .eq(Contact::getUid, uid)
                .one();
    }
}
