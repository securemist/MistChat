package cc.xmist.mistchat.server.chat.entity;

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
 * @since 2024-03-11
 */
@Data
@Builder
@TableName("group_contact")
public class GroupContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户 id
     */
    @TableField("uid")
    private Long uid;

    @TableField("group_id")
    private Long groupId;

    /**
     * 已经阅读到的消息 id
     */
    @TableField("read_msg_id")
    private Long readMsgId;

    /**
     * 最新的消息id
     */
    @TableField("last_msg_id")
    private Long lastMsgId;

    /**
     * 在群聊中的最后一次活跃时间
     */
    @TableField("active_time")
    private LocalDateTime activeTime;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
