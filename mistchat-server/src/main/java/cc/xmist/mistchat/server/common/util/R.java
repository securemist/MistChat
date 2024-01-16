package cc.xmist.mistchat.server.common.util;

import lombok.Data;

@Data
public class R<T> {
    private Boolean success;
    private Integer errCode;
    private String errMsg;
    private T data;

    private R(T data) {
        this.success = true;
        this.data = data;
    }

    private R(Integer errCode, String errMsg) {
        this.success = false;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public static R ok(Object data) {
        return new R(data);
    }

    public static R error(ErrorType errorType) {
        return new R(errorType.code, errorType.msg);
    }

    public static R commonError(String errMsg) {
        return new R(ErrorType.UNKNOWN_FAILED.code, errMsg);
    }
}
