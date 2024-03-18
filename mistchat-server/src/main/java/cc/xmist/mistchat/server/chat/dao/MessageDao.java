package cc.xmist.mistchat.server.chat.dao;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.mapper.MessageMapper;
import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.util.Cursor;
import cc.xmist.mistchat.server.common.util.CursorResult;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

    // TODO 该方法存在 bug，设置的 pageSize 失效，会返回全部数据
    public List<Message> page(Long chatId, ChatType chatType, String cursorValue, Long pageSize) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        Cursor<Long> cursor = Cursor.buildLong(cursorValue, pageSize);

        if (cursor.getValue() != null) {
            wrapper.lt(Message::getId, cursor.getValue());
        }

        wrapper.orderByDesc(Message::getId);
        wrapper.eq(Message::getChatId, chatId);
        wrapper.eq(Message::getChatType, chatType);

        Page<Message> page = baseMapper.selectPage(new Page<Message>(1, pageSize, pageSize, false), wrapper);
        return page.getRecords();
    }

    public List<Message> listCursorable(Long chatId, ChatType chatType, Cursor<Long> cursor) {
        return baseMapper.selectByIdCursorable(chatId, chatType.getCode(), cursor.getValue(), cursor.getPageSize());
    }

}
