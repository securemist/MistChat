package cc.xmist.mistchat.server.socketio.model;

import lombok.Data;

@Data
public class SocketResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> SocketResponse build(SocketResponseType responseType, T data) {
        SocketResponse<T> response = new SocketResponse<>();
        response.setCode(responseType.getCode());
        response.setMsg(responseType.getMsg());
        response.setData(data);
        return response;
    }

    public static <T> SocketResponse build(SocketResponseType responseType) {
        return build(responseType);
    }
}
