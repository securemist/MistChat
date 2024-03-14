package cc.xmist.mistchat.server.user.model.entity;

import cc.xmist.mistchat.server.user.model.enums.Item;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 功能物品配置表
 * </p>
 *
 * @author securemist
 * @since 2024-01-16
 */
@Getter
@Setter
@TableName("item_config")
public class ItemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;

    /**
     * 物品类型
     */
    @TableField("type")
    private Item.Type type;

    /**
     * 物品图片
     */
    @TableField("img")
    private String img;

    /**
     * 物品功能描述
     */
    @TableField("description")
    private String description;

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
