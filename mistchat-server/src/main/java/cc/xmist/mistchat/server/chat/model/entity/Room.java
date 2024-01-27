package cc.xmist.mistchat.server.chat.model.entity;

import cc.xmist.mistchat.server.chat.model.enums.RoomType;
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
 * @since 2024-01-27
 */
@Getter
@Setter
@TableName("room")
@Builder
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间类型，单聊 OR 群聊
     */
    @TableField("type")
    private RoomType type;

    /**
     * 热点房间
     */
    @TableField("hot")
    private String hot;

    /**
     * 活跃时间，最新的一条消息的时间
     */
    @TableField("active_time")
    private LocalDateTime activeTime;

    /**
     * 最新的消息id
     */
    @TableField("last_msg_id")
    private Long lastMsgId;

    /**
     * 补充
     */
    @TableField("extra")
    private String extra;

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
