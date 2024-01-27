package cc.xmist.mistchat.server.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpDetail {
    private String area;
    private String country;
    private String city;
    private String isp;
    private String county;
    private String region;
}
