package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.entity.MessageExtra;
import cc.xmist.mistchat.server.chat.mapper.MessageMapper;
import cc.xmist.mistchat.server.chat.message.extra.SystemMsgExtra;
import cc.xmist.mistchat.server.common.enums.MessageType;
import cc.xmist.mistchat.server.common.enums.SystemMsgType;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

    public List<Message> listCursorable(Long contactId, String cursor, Integer pageSize) {
        return lambdaQuery()
                .lt(StrUtil.isBlank(cursor), Message::getId, cursor)
                .eq(Message::getContactId, contactId)
                .orderByDesc(Message::getId)
                .last("LIMIT " + pageSize)
                .list();
    }

    public Long[] initOnFriend(Long uid, Long contactId1, Long targetUid, Long contactId2) {
        MessageExtra extra = MessageExtra.builder()
                .sysMsgExtra(new SystemMsgExtra(SystemMsgType.BE_FRIEND))
                .build();

        Message m1 = Message.builder()
                .type(MessageType.SYSTEM)
                .extra(extra)
                .contactId(contactId1)
                .build();

        Message m2 = Message.builder()
                .type(MessageType.SYSTEM)
                .extra(extra)
                .contactId(contactId2)
                .build();

        saveBatch(Arrays.asList(m1, m2));

        return new Long[]{m1.getId(),m2.getId()};
    }
}
