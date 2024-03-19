package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.mapper.MessageMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

    public List<Message> listCursorable(Long contactId, String cursor, Integer pageSize) {
        return lambdaQuery()
                .lt(StrUtil.isBlank(cursor), Message::getId, cursor)
                .eq(Message::getContactId,contactId)
                .orderByDesc(Message::getId)
                .last("LIMIT " + pageSize)
                .list();
    }
}
