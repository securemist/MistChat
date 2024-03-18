package cc.xmist.mistchat.server.chat.entity;

import cc.xmist.mistchat.server.common.enums.ChatType;
import cc.xmist.mistchat.server.common.enums.MessageType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.A;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "message",autoResultMap = true)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间号
     */
    @TableField("chat_id")
    private Long chatId;

    @TableField("chat_type")
    private ChatType chatType;

    /**
     * 发送者
     */
    @TableField("uid")
    private Long uid;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 回复的消息id
     */
    @TableField("reply_msg_id")
    private Long replyMsgId;

    /**
     * 与回复的消息的间隔数
     */
    @TableField("reply_gap")
    private Long replyGap;

    @TableField("type")
    private MessageType type;

    @TableField(value = "extra",typeHandler = JacksonTypeHandler.class)
    private MessageExtra extra;

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
