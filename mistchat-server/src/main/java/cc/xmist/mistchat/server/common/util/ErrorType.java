package cc.xmist.mistchat.server.common.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorType {

    UNKNOWN_FAILED(4000, "服务器异常"),
    NOT_LOGIN(4001, "登陆失效，请重新登陆"),
    PARAM_ERROR(4002, "请求参数异常");

     Integer code;
     String msg;
}
