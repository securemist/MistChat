package cc.xmist.mistchat.server.friend.entity;

import cc.xmist.mistchat.server.common.enums.ApplyStatus;
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
 * 用户申请表
 * </p>
 *
 * @author securemist
 * @since 2024-03-11
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("friend_apply")
public class FriendApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请人uid
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 接收人uid
     */
    @TableField("target_uid")
    private Integer targetUid;

    /**
     * 申请信息
     */
    @TableField("apply_msg")
    private String applyMsg;

    /**
     * 回复消息
     */
    @TableField("reply_msg")
    private String replyMsg;

    /**
     * 申请状态 拒绝 待审批 同意
     */
    @TableField("status")
    private ApplyStatus status;

    /**
     * 阅读时间
     */
    @TableField("read_time")
    private LocalDateTime readTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
