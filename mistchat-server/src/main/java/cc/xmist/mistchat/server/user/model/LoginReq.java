package cc.xmist.mistchat.server.user.model;

import lombok.Data;

@Data
public class LoginReq {
    private String username;
    private String password;
}
