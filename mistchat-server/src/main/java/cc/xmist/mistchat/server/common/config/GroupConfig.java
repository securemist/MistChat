package cc.xmist.mistchat.server.common.config;

import cc.xmist.mistchat.server.common.enums.RoomType;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GroupConfig {
    private static final int GROUPID_LEN = 7;

    /**
     * 生成 7 位群聊 id
     *
     * @return
     */
    public static String  genGroupId() {
        LocalDateTime now = LocalDateTime.now();
        int dayOfYear = now.getDayOfYear();

        String s1 = String.valueOf(RandomUtil.randomInt(1, 9)); // 1位随机数
        String s2 = String.valueOf(dayOfYear * (now.getHour() <= 12 ? 1 : 2)); // 3位 一年中的第几天，前半天× 1，后半天×2

        String time = String.valueOf(System.currentTimeMillis()); // 3位 时间戳 ms
        String s3 = time.substring(time.length() - 3); //

        return new StrBuilder().append(s1).append(s2).append(s3).toString();
    }

    /**
     * 根据 roomId 的长度判断是群聊还是单聊
     * @param roomId
     * @return
     */
    public static RoomType getRoomType(String  roomId) {
        return roomId.length() == GROUPID_LEN
                ? RoomType.GROUP
                : RoomType.FRIEND;
    }
}
