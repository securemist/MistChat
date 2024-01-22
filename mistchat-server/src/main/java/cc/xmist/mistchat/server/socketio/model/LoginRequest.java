package cc.xmist.mistchat.server.socketio.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
