package cc.xmist.mistchat.server.chat.model.dao;

import cc.xmist.mistchat.server.chat.model.entity.FriendContact;
import cc.xmist.mistchat.server.chat.model.mapper.FriendContactMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FriendContactDao extends ServiceImpl<FriendContactMapper, FriendContact> {
    public void firstCreate(Long friendId, Long uid1, Long uid2) {
        FriendContact f1 = FriendContact.builder()
                .friendId(friendId)
                .uid(uid1)
                .build();

        FriendContact f2 = FriendContact.builder()
                .friendId(friendId)
                .uid(uid1)
                .build();

        saveBatch(Arrays.asList(f1, f2));
    }
}
