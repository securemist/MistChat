package cc.xmist.mistchat.server.friend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户联系人表
 * </p>
 *
 * @author securemist
 * @since 2024-01-27
 */
@TableName("friend")
@NoArgsConstructor
@Data
public class Friend implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * uid
     */
    @TableField("uid1")
    private Integer uid1;

    /**
     * 好友uid
     */
    @TableField("uid2")
    private Integer uid2;

    @TableField("room_id")
    private String  roomId;

    /**
     * 逻辑删除时间
     */
    @TableField("delete_time")
    private LocalDateTime deleteTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


    public Friend(Integer uid1, Integer uid2) {
        int min = Math.min(uid1, uid2);
        int max = Math.max(uid1, uid2);
        this.uid1 = min;
        this.uid2 = max;
        this.roomId = String.valueOf(uid1) + String.valueOf(uid2);
    }

}
