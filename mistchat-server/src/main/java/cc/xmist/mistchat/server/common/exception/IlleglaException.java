package cc.xmist.mistchat.server.common.exception;

public class IlleglaException extends RuntimeException{
    public IlleglaException() {
        super("非法操作");
    }
}
