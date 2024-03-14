package cc.xmist.mistchat.server.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class ThreadPoolConfig implements AsyncConfigurer {

    public static final String BEAN_NAME = "mistchatExecutor";
    private static final int CORE_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 200;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = mistchatExecutor();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                log.error(String.valueOf(method.getDeclaringClass()), ex.getMessage());
            }
        };
    }

    @Bean(BEAN_NAME)
    @Primary
    public ThreadPoolTaskExecutor mistchatExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setWaitForTasksToCompleteOnShutdown(true); // 优雅停机
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(CORE_POOL_SIZE * 2);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix("mistchat-executor-");
        executor.setThreadFactory(new MyThreadFactory(executor));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略，线程池满了之后让调用者自己处理
        executor.initialize();
        return executor;
    }
}
