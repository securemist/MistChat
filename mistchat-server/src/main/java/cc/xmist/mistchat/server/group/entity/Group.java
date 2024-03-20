package cc.xmist.mistchat.server.group.entity;

import cc.xmist.mistchat.server.common.enums.GroupStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author securemist
 * @since 2024-03-14
 */
@Data
@Builder
@TableName("`group`")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间号
     */
      @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 群聊名
     */
    @TableField("name")
    private String name;

    @TableField("creater_uid")
    private Long createrUid;

    @TableField("owner_uid")
    private Long ownerUid;

    @TableField("avatar")
    private String avatar;

    @TableField("extra")
    private String extra;

    @TableField("status")
    private GroupStatus status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
