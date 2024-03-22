package cc.xmist.mistchat.server.chat.entity;

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
    private Integer id;

    /**
     *
     */
    @TableField(value = "uid")
    private Integer uid;

    @TableField(value = "room_id")
    private String  roomId;

    /**
     * 已经读到的消息 id
     */
    @TableField(value = "read_msg_id")
    private Integer readMsgId;

    /**
     * 会话中的最新消息 id
     */
    @TableField(value = "active_msg_id")
    private Integer activeMsgId;

    /**
     *
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private Integer unreadCount;
}