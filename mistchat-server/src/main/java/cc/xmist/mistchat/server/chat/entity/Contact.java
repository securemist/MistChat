package cc.xmist.mistchat.server.chat.entity;

import cc.xmist.mistchat.server.common.enums.ChatType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName contact
 */
@TableName(value = "contact")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @TableField(value = "uid")
    private Long uid;

    @TableField(value = "room_id")
    private Long roomId;

    /**
     * 已经读到的消息 id
     */
    @TableField(value = "read_msg_id")
    private Long readMsgId;

    /**
     * 会话中的最新消息 id
     */
    @TableField(value = "last_msg_id")
    private Long lastMsgId;

    /**
     *
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     *
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private Long unreadCount;
}