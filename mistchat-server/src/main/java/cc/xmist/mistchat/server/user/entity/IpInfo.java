package cc.xmist.mistchat.server.user.entity;

import lombok.Data;

@Data
public class IpInfo {
    private String initialIp;
    private IpDetail initialIpDetail;
    private String lastIp;
    private IpDetail lastIpDetail;
}
