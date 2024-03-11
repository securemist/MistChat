package cc.xmist.mistchat.server.user.model.mapper;

import cc.xmist.mistchat.server.user.model.entity.UserApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户申请表 Mapper 接口
 * </p>
 *
 * @author securemist
 * @since 2024-01-27
 */
public interface UserApplyMapper extends BaseMapper<UserApply> {

    boolean isFriend(@Param("uid1") Long uid1, @Param("uid2")Long uid2);
}
