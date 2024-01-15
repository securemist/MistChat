package cc.xmist.mistchat.server.common.config;

import lombok.extern.slf4j.Slf4j;

/**
 * 子线程未捕获异常处理
 */
@Slf4j
public class MyUncaughtExceptionHandle implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error(e.getMessage());
    }
}
