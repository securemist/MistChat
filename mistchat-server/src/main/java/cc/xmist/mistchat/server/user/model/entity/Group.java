package cc.xmist.mistchat.server.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author securemist
 * @since 2024-03-14
 */
@Getter
@Setter
@TableName("group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群聊名
     */
    @TableField("name")
    private String name;

    /**
     * 群聊头像
     */
    @TableField("avatar")
    private String avatar;

    @TableField("extra")
    private String extra;

    @TableField("status")
    private String status;

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
