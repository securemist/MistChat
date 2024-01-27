package cc.xmist.mistchat.server.user.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class IpInfo {
    private String initialIp;
    private IpDetail initialIpDetail;
    private String lastIp;
    private IpDetail lastIpDetail;
}
