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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

    public List<Message> listCursorable(Integer roomId, String cursor, Integer pageSize) {
        return lambdaQuery()
                .lt(StrUtil.isBlank(cursor), Message::getId, cursor)
                .eq(Message::getRoomId,roomId)
                .orderByDesc(Message::getId)
                .last("LIMIT " + pageSize)
                .list();
    }

    public List<Integer> initGroupMsg(List<Integer> contactIds) {
        MessageExtra extra = MessageExtra.builder()
                .sysMsgExtra(new SystemMsgExtra(SystemMsgType.JOIN_GROUP))
                .build();

        ArrayList<Integer> list = new ArrayList<>();
        List<Message> messages = contactIds.stream().map(contactId -> {
            return Message.builder()
                    .type(MessageType.SYSTEM)
                    .extra(extra)
                    .build();
        }).collect(Collectors.toList());

        saveBatch(messages);

        return messages.stream()
                .map(Message::getId)
                .collect(Collectors.toList());
    }

    /**
     * 计算聊天室内消息的未读数
     *
     * @param roomId    聊天室 id
     * @param readMsgId 已读消息
     * @param lastMsgId 最新消息
     */
    public Integer calUnread(String  roomId, Integer readMsgId, Integer lastMsgId) {
        Long count = lambdaQuery()
                .eq(Message::getRoomId, roomId)
                .gt(Message::getId, readMsgId)
                .le(Message::getId, lastMsgId)
                .count();
        return count.intValue();
    }
}
