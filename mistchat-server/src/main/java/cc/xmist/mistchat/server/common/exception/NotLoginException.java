package cc.xmist.mistchat.server.common.exception;

public class NotLoginException extends RuntimeException{
    public NotLoginException() {
    }

    public NotLoginException(String message) {
        super(message);
    }
}
