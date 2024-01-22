package cc.xmist.mistchat.server.common.exception;

public class ParamException extends RuntimeException {
    public ParamException(String message) {
        super(message);
    }

    public ParamException() {
        super("请求参数异常");
    }
}
