package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.common.enums.IdempotentType;
import cc.xmist.mistchat.server.common.enums.Item;
import cc.xmist.mistchat.server.common.util.JwtUtil;
import cc.xmist.mistchat.server.user.entity.IpDetail;
import cc.xmist.mistchat.server.user.service.AuthService;
import cc.xmist.mistchat.server.user.service.IpService;
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

    private JwtUtil jwtUtil = new JwtUtil("adsa");

    @Test
    public void testGetRoomUsers() {
//        Long roomId = 2L;
//        log.info("单聊结果");
//        roomService.getRoomUsers(roomId).forEach(uid -> {
//            log.info("{}", uid);
//        });
//
//        log.info("群聊结果");
//        Long roomId2 = 1L;
//        roomService.getRoomUsers(roomId2).forEach(uid -> {
//            log.info("{}", uid);
//        });

    }

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
        itemService.acquireItem(uid, Item.MODIFY_NAME_CARD, IdempotentType.UID, "123");
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
