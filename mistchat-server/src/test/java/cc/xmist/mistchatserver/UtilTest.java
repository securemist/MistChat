package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.user.model.req.LoginRequest;
import com.google.gson.Gson;
import org.junit.Test;

public class UtilTest {
    @Test
    public void testJson(){
        LoginRequest loginReq = new LoginRequest("18115168320", "123456");
        String json = new Gson().toJson(loginReq);
        System.out.println(json);
    }
    @Test
    public void testIpUtil(){
//        IpDetail ipDetail = IpUtil.getIpDetail("124.223.207.249");
//        System.out.println(ipDetail);
    }

    @Test
    public void testEnum() {
        System.out.println(SexType.M == SexType.M);
    }
}
