package cc.xmist.mistchat.server.chat.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author securemist
 * @since 2024-01-26
 */
@Getter
@Setter
@Builder
@TableName("room_friend")
public class RoomFriend implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间号
     */
    @TableField("room_id")
    private Long roomId;

    @TableField("uid1")
    private Long uid1;

    @TableField("uid2")
    private Long uid2;

    /**
     * 房间唯一标识
     */
    @TableField("room_key")
    private String roomKey;

    /**
     * 房间状态
     */
    @TableField("status")
    private String status;

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
