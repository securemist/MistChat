package cc.xmist.mistchat.server.common.exception;

public class IllegalParamException extends RuntimeException{
    public IllegalParamException() {
        super("非法操作");
    }
}
