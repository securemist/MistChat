package cc.xmist.mistchat.server.chat.service;

import cc.xmist.mistchat.server.chat.model.dao.GroupMemberDao;
import cc.xmist.mistchat.server.chat.model.dao.RoomDao;
import cc.xmist.mistchat.server.chat.model.dao.RoomFriendDao;
import cc.xmist.mistchat.server.chat.model.dao.RoomGroupDao;
import cc.xmist.mistchat.server.chat.model.entity.Room;
import cc.xmist.mistchat.server.chat.model.entity.RoomFriend;
import cc.xmist.mistchat.server.chat.model.entity.RoomGroup;
import cc.xmist.mistchat.server.chat.model.enums.RoomType;
import cc.xmist.mistchat.server.common.exception.ParamException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomService {
    @Resource
    private RoomDao roomDao;
    @Resource
    private RoomFriendDao roomFriendDao;
    @Resource
    private RoomGroupDao roomGroupDao;
    @Resource
    private GroupMemberDao groupMemberDao;

    /**
     * 创建好友聊天室
     * 在两人成功添加好友的时候创建
     *
     * @param uid     创建者
     * @param request
     * @return 聊天室的id
     */
    public Long createFriendRoom(Long uid1, Long uid2) {
        Room room = roomDao.create(RoomType.FRIEND);
        RoomFriend roomFriend = roomFriendDao.create(room.getId(), uid1, uid2);
        return room.getId();
    }

    private String getRoomKey(Long uid1, Long uid2) {
        if (uid1 < uid2) {
            return uid1 + "_" + uid2;
        } else {
            return uid2 + "_" + uid1;
        }
    }

    private static void checkUidList(List<Long> uidList, RoomType type) {
        if (uidList == null || uidList.size() == 0) {
            throw new ParamException();
        }

        if (type.equals(RoomType.FRIEND) && uidList.size() > 1) {
            throw new ParamException();
        }
    }


    /**
     * 获取聊天室里的所有用户
     *
     * @param roomId
     * @return 所有成员的id集合
     * @apiNote 单聊也会返回两个人的id
     */
    public List<Long> getRoomUsers(Long roomId) {
        Room room = roomDao.getById(roomId);
        List<Long> uidList = null;
        switch (room.getType()) {
            case FRIEND -> uidList = roomFriendDao.getMembers(roomId);
            case GROUP -> {
                RoomGroup group = roomGroupDao.getByRoomId(roomId);
                uidList = groupMemberDao.getMembersByGroupId(group.getId());
            }
        }
        return uidList;
    }

    public Room getById(Long roomId) {
        return roomDao.getById(roomId);
    }

    public RoomType getRoomType(Long roomId) {
        return getById(roomId).getType();
    }
}
