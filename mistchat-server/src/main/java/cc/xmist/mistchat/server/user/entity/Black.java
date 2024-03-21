package cc.xmist.mistchat.server.user.model.entity;

import cc.xmist.mistchat.server.common.enums.BlackType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 黑名单
 * </p>
 *
 * @author securemist
 * @since 2024-01-25
 */
@Getter
@Setter
@TableName("black")
@Builder
public class Black implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("uid")
    private Long uid;

    @TableField("type")
    private BlackType type;

    @TableField("target")
    private String target;

    @TableField("create_time")
    private LocalDateTime createTime;
}
