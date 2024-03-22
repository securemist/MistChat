package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.common.util.RedisUtil;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis() {

        stringRedisTemplate.opsForValue().set("name", "111");
        String name = (String) stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    public void testRedisUtil() {
        RedisUtil.set("type", "aa");
        String type = RedisUtil.get("type", String.class);
        System.out.println(type);

        RedisUtil.del("type");
    }

}
