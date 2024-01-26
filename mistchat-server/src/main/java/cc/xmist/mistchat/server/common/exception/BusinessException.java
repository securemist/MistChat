package cc.xmist.mistchat.server.common.exception;

public class BusinessException extends  RuntimeException{
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
