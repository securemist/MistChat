package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.Room;
import cc.xmist.mistchat.server.chat.model.enums.ChatType;
import cc.xmist.mistchat.server.chat.model.mapper.RoomMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoomDao extends ServiceImpl<RoomMapper, Room> {

    public ChatType getRoomType(Long roomId) {
        return getById(roomId).getType();
    }

    public Room create(ChatType type) {
        Room room = Room.builder()
                .type(type)
                .build();
        save(room);
        return room;
    }
}
