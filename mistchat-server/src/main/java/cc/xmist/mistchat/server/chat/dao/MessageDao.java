package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.mapper.MessageMapper;
import cc.xmist.mistchat.server.common.enums.ChatType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

    public List<Message> listCursorable(Long chatId, ChatType chatType, String cursor, Integer pageSize) {
        return lambdaQuery()
                .lt(cursor != null, Message::getId, cursor)
                .eq(Message::getChatId, chatId)
                .eq(Message::getChatType, chatType)
                .orderByDesc(Message::getId)
                .last("LIMIT " + pageSize)
                .list();
    }
}
