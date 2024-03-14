package cc.xmist.mistchatserver;

import cc.xmist.mistchat.server.chat.model.dao.FriendContactDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {
    @Autowired
    private FriendContactDao friendContactDao;
}

