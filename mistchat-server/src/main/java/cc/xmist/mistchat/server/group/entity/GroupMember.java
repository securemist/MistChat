package cc.xmist.mistchat.server.group.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author securemist
 * @since 2024-01-26
 */
@TableName("group_member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间号
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 用户
     */
    @TableField("uid")
    private Long uid;

    /**
     * 在群聊中的最后一次活跃时间
     */
    @TableField("active_time")
    private LocalDateTime activeTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

}
