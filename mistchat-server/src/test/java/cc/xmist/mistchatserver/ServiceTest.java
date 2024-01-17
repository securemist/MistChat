package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.common.util.JwtUtil;
import cc.xmist.mistchat.server.user.service.AuthService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Resource
    private AuthService authService;

    @Test
    public void testAuth() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjIwMDAwLCJjcmVhdGVUaW1lIjoxNzA1Mjg3MzQ0fQ.9788v3v7HxDJCGS2KDymnDnr6vSQuUxdwfTqvBfCRaY";
        Long uid = authService.verify(token);
        System.out.println(uid);
    }


        @Resource
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt() {
        String token = jwtUtil.createToken(123L);
        System.out.println(token);

        Long uid1 = jwtUtil.getUid(token);
        System.out.println(uid1);
    }


    @Test
    public void testAsync(){
//        authService.refreshToken("111");
    }

}
