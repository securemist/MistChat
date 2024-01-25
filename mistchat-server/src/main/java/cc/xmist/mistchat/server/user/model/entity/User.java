package cc.xmist.mistchat.server.user.model.entity;

import cc.xmist.mistchat.server.common.util.JsonUtil;
import cc.xmist.mistchat.server.user.model.IpInfo;
import cc.xmist.mistchat.server.user.model.enums.ActiveType;
import cc.xmist.mistchat.server.user.model.enums.RoleType;
import cc.xmist.mistchat.server.user.model.enums.SexType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ${author}
 * @since 2024-01-11
 */
@Data
@TableName(value = "user")
@Builder
public class User extends Model<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    @TableField("name")
    private String name;

    /**
     * 登陆用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别
     */
    @TableField("sex")
    private SexType sex;

    /**
     * 微信openid用户标识
     */
    @TableField("open_id")
    private String openId;

    /**
     * 角色
     */
    @TableField("role")
    private RoleType role;
    /**
     * 在线状态
     */
    @TableField("active_status")
    private ActiveType activeStatus;

    /**
     * 最后上下线时间
     */
    @TableField("last_opt_time")
    private Date lastOptTime;

    /**
     * ip信息
     */
    @TableField(value = "ip_info")
    private String ipInfo;

    public IpInfo getIpInfo() {
        return JsonUtil.toObj(this.ipInfo, IpInfo.class);
    }

    public void setIpInfo(IpInfo ipInfo) {
        this.ipInfo = JsonUtil.toJson(ipInfo);
    }

    /**
     * 佩戴的徽章id
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 使用状态 0.正常 1拉黑
     */
    @TableField("status")
    private Integer status;

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
