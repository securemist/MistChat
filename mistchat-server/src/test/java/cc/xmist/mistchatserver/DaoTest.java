package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.user.dao.UserApplyDao;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {
    @Resource
    private UserApplyDao userApplyDao;

    @Test
    public void testIsFriend() {
        boolean friend = userApplyDao.isFriend(20001L, 20002L);
        System.out.println(friend);
    }
}
