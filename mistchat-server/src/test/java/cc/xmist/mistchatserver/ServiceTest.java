package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.common.util.JwtUtil;
import cc.xmist.mistchat.server.user.model.IpDetail;
import cc.xmist.mistchat.server.user.model.enums.IdempotentType;
import cc.xmist.mistchat.server.user.model.enums.ItemType;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.IpService;
import cc.xmist.mistchat.server.user.service.ItemService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ServiceTest {

    @Resource
    AuthService authService;
    @Resource
    ItemService itemService;
    @Resource
    IpService ipService;

    @Resource
    private JwtUtil jwtUtil;


    @Test
    public void testAuth() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjIwMDAwLCJjcmVhdGVUaW1lIjoxNzA1Mjg3MzQ0fQ.9788v3v7HxDJCGS2KDymnDnr6vSQuUxdwfTqvBfCRaY";
        Long uid = authService.verify(token);
        System.out.println(uid);
    }


    @Test
    public void testCreateJwt() {
        String token = jwtUtil.createToken(123L);
        System.out.println(token);

        Long uid1 = jwtUtil.getUid(token);
        System.out.println(uid1);
    }


    @Test
    public void testAcquireItem() {
        Long uid = 20001L;
        itemService.acquireItem(uid, ItemType.MODIFY_NAME_CARD, IdempotentType.UID, "123");
    }

    @Test
    public void testGetIpInfo() {
        String ip = "124.223.207.249";

        Long interval = 500L;
        log.info("测试ip解析接口，间隔 {}ms", interval);
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            IpDetail ipDetail = ipService.getIpDetailOrNull(ip);
            log.info("{}, {}, 耗时:{}", i, ipDetail != null ? "success" : "failed", System.currentTimeMillis() - start);
        }
    }
}
