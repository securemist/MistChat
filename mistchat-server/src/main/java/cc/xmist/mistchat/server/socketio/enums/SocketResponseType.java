package cc.xmist.mistchat.server.socketio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SocketResponseType {
    LOGIN_SUCCESS(1, "登陆成功"),
    LOGIN_FAILED(2, "登陆失败，用户名或密码错误"),
    INVALIDATE_TOKEN(6, "无效的token");

    private Integer code;
    private String msg;
}
