package cc.xmist.mistchatserver;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ThreadTest {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Test
    public void testThreadPool() throws InterruptedException {
        threadPoolTaskExecutor.execute(() -> {
//            int i = 1 / 0;
            throw new RuntimeException("1234");
        });
        Thread.sleep(2000);
    }


    @Test
    public void testThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("1234");
        });
//        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandle());
        thread.start();
        Thread.sleep(2000);
    }

}
