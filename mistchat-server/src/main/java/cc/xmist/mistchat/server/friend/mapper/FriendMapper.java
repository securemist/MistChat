package cc.xmist.mistchat.server.friend.mapper;

import cc.xmist.mistchat.server.friend.entity.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户联系人表 Mapper 接口
 * </p>
 *
 * @author securemist
 * @since 2024-01-27
 */
public interface FriendMapper extends BaseMapper<Friend> {
    boolean isFriend(@Param("uid1") Long uid1, @Param("uid2") Long uid2);
}
