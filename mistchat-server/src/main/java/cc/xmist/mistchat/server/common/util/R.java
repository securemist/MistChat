package cc.xmist.mistchat.server.common.util;

import lombok.Data;

@Data
public class R<T> {
    private static Integer SUCCESS_CODE = 0;
    private Integer code;
    private String msg;
    private T data;

    private R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> R ok(T data) {
        return new R(SUCCESS_CODE, null, data);
    }

    public static <T> R ok() {
        return R.ok(null);
    }

    public static R error(ErrorType errorType) {
        return new R(errorType.code, errorType.msg);
    }

    public static R paramError() {
        return R.error(ErrorType.PARAM_ERROR);
    }

    public static R paramError(String message) {
        return new R(ErrorType.PARAM_ERROR.code, message);
    }

    public static R commonError(String errMsg) {
        return new R(ErrorType.UNKNOWN_FAILED.code, errMsg);
    }

    public static R commonError() {
        return R.error(ErrorType.UNKNOWN_FAILED);
    }
}
