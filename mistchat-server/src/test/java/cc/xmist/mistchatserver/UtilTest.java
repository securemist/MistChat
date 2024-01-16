package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.user.model.req.LoginReq;
import com.google.gson.Gson;
import org.junit.Test;

public class UtilTest {
    @Test
    public void testJson(){
        LoginReq loginReq = new LoginReq("18115168320", "123456");
        String json = new Gson().toJson(loginReq);
        System.out.println(json);
    }
}
