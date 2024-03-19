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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Long> initGroupMsg(List<Long> contactIds) {
        MessageExtra extra = MessageExtra.builder()
                .sysMsgExtra(new SystemMsgExtra(SystemMsgType.JOIN_GROUP))
                .build();

        ArrayList<Long> list = new ArrayList<>();
        List<Message> messages = contactIds.stream().map(contactId -> {
            return Message.builder()
                    .type(MessageType.SYSTEM)
                    .extra(extra)
                    .contactId(contactId)
                    .build();
        }).collect(Collectors.toList());

        saveBatch(messages);

        return messages.stream()
                .map(Message::getId)
                .collect(Collectors.toList());
    }

    public List<Long> initSystemMsg(List<Long> contactIds, SystemMsgType msgType) {
        MessageExtra extra = MessageExtra.builder()
                .sysMsgExtra(new SystemMsgExtra(msgType))
                .build();

        List<Message> messages = contactIds.stream().map(contactId -> {
            return Message.builder()
                    .type(MessageType.SYSTEM)
                    .extra(extra)
                    .contactId(contactId)
                    .build();
        }).collect(Collectors.toList());
        saveBatch(messages);
        return messages.stream()
                .map(Message::getId)
                .collect(Collectors.toList());
    }

    public List<Long> initFriendMsg(List<Long> contactIds) {
        MessageExtra extra = MessageExtra.builder()
                .sysMsgExtra(new SystemMsgExtra(SystemMsgType.BE_FRIEND))
                .build();

        List<Message> messages = contactIds.stream().map(contactId -> {
            return Message.builder()
                    .type(MessageType.SYSTEM)
                    .extra(extra)
                    .contactId(contactId)
                    .build();
        }).collect(Collectors.toList());
        saveBatch(messages);
        return messages.stream()
                .map(Message::getId)
                .collect(Collectors.toList());
    }
}
