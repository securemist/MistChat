package cc.xmist.mistchat.server.common.util;

import lombok.Data;

@Data
public class R {
    private Boolean success;
    private Integer errCode;
    private String errMsg;
    private Object data;

    private R(Object data) {
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

    public static R error(ErrorEnum errorEnum) {
        return new R(errorEnum.getErrorCode(), errorEnum.getErrorMsg());
    }

    public static R commonError(String errMsg) {
        return new R(1001, errMsg);
    }
}
