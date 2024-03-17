package cc.xmist.mistchat.server.group.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@TableName("group_apply")
public class GroupApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请人uid
     */
    @TableField("uid")
    private Long uid;

    /**
     * 接收人uid
     */
    @TableField("group_id")
    private Long groupId;

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
    private Integer status;

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

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
