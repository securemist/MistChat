package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.mapper.MessageMapper;
import cc.xmist.mistchat.server.chat.model.req.FriendMessageReq;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-26
 */
@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {
    public void save(Long uid, FriendMessageReq request) {
        Message message = Message.builder()
                .uid(uid)
                .type(request.getType())
                .roomId(request.getRoomId())
                .build();
        save(message);
    }
}
