package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.FriendContact;
import cc.xmist.mistchat.server.chat.model.mapper.FriendContactMapper;
import cc.xmist.mistchat.server.friend.dao.FriendDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FriendContactDao extends ServiceImpl<FriendContactMapper, FriendContact> {
    @Resource
    private FriendDao friendDao;

    public List<FriendContact> getByUid(Long uid) {
        return lambdaQuery()
                .eq(FriendContact::getUid, uid)
                .list();
    }

    public FriendContact getById(Long id) {
        return getById(id);
    }

    public void firstCreate(Long uid1, Long uid2) {
        FriendContact f1 = FriendContact.builder()
                .friendUid(uid2)
                .uid(uid1)
                .build();

        FriendContact f2 = FriendContact.builder()
                .friendUid(uid1)
                .uid(uid2)
                .build();

        saveBatch(Arrays.asList(f1, f2));
    }
}
