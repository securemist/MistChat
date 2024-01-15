package cc.xmist.mistchat.server.common.config;

import lombok.extern.slf4j.Slf4j;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

/**
 * 可捕获异常的线程工厂，用于替换spring自带线程池ThreadPoolTaskExecutor的线程工厂
 * spring自带的线程工厂，处理子线程未捕获的异常方法是控制台打印，我们这里需要替换成自己的
 * 使用装饰器模式
 *
 * 传入原有的线程工厂，在其基础上添加异常处理的逻辑
 */
@Slf4j
public class MyThreadFactory implements ThreadFactory {
    // 子线程异常的处理方法，使用自定义的log
    public static final UncaughtExceptionHandler uncaughtExceptionHandler = new MyUncaughtExceptionHandle();

    private ThreadFactory originalFactory;

    public MyThreadFactory(ThreadFactory originalFactory) {
        this.originalFactory = originalFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = originalFactory.newThread(r);

        // 自定义逻辑
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);

        return thread;
    }
}
