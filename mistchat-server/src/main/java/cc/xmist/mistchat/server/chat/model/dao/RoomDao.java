package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.Room;
import cc.xmist.mistchat.server.chat.model.enums.RoomType;
import cc.xmist.mistchat.server.chat.model.mapper.RoomMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author securemist
 * @since 2024-01-27
 */
@Service
public class RoomDao extends ServiceImpl<RoomMapper, Room> {

    public RoomType getRoomType(Long roomId) {
        return getById(roomId).getType();
    }

    public Room create(RoomType type) {
        Room room = Room.builder()
                .type(type)
                .build();
        save(room);
        return room;
    }
}
