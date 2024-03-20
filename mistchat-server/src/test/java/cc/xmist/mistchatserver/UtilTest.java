package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.common.enums.Item;
import cc.xmist.mistchat.server.user.model.req.LoginReq;
import com.google.gson.Gson;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UtilTest {
    @Test
    public void testJson() {
        LoginReq loginReq = new LoginReq("18115168320", "123456");
        String json = new Gson().toJson(loginReq);
        System.out.println(json);

        for (Item value : Item.values()) {
            System.out.println(value);
        }
    }

    @Test
    public void testIpUtil() {
//        IpDetail ipDetail = IpUtil.getIpDetail("124.223.207.249");
//        System.out.println(ipDetail);
    }

    @Test
    public void testFilter() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        List<Integer> l = list.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toList());

        for (Integer i : l) {
            System.out.println(i);
        }
    }

    @Test
    public void testConsumer() {
        add(0, zero -> {
            System.out.println(zero + "1");
        });

        add(1, zero -> {
            System.out.println(zero + "2");
        });
    }

    public void add(int con, Consumer<Integer> zero) {
        if (con == 0) {
            zero.accept(0);
        }
    }
}
