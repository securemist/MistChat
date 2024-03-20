package cc.xmist.mistchat.server.friend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
    private Long id;

    /**
     * uid
     */
    @TableField("uid1")
    private Long uid1;

    /**
     * 好友uid
     */
    @TableField("uid2")
    private Long uid2;


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

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    public Friend(Long uid1, Long uid2) {
        long min = Math.min(uid1, uid2);
        long max = Math.max(uid1, uid2);
        this.uid1 = min;
        this.uid2 = max;
    }

    public Long getRoomId() {
        return Long.valueOf(String.valueOf(uid1) + String.valueOf(uid2));
    }

    public static List<Long> parseRoomId(Long roomId) {
        return Arrays.asList(
                Long.valueOf(String.valueOf(roomId).substring(0, 5)),
                Long.valueOf(String.valueOf(roomId).substring(6, 10))
        );
    }
}
