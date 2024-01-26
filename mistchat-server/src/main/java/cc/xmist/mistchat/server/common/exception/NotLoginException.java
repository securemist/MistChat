package cc.xmist.mistchat.server.common.exception;

public class NotLoginException extends BusinessException{
    public NotLoginException() {
    }

    public NotLoginException(String message) {
        super(message);
    }
}
