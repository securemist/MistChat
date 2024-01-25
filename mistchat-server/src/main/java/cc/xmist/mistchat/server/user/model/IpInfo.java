package cc.xmist.mistchat.server.user.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class IpInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String initialIp;
    private IpDetail initialIpDetail;
    private String lastIp;
    private IpDetail lastIpDetail;
}
