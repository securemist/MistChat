package cc.xmist.mistchat.server.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class IpDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private String area;
    private String country;
    private String city;
    private String isp;
    private String county;
    private String region;
}
