package cc.xmist.mistchat.server.common.util;

import cc.xmist.mistchat.server.common.enums.RoomType;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

public class IdUtil {

    private static final int GROUPID_LEN = 7;

    /**
     * 生成 7 位群聊 id
     *
     * @return
     */
    public static Long genGroupId() {
        String time = String.valueOf(System.currentTimeMillis());
        String s1 = time.substring(time.length() - 3);
        String s2 = String.valueOf(RandomUtil.randomNumbers(GROUPID_LEN - s1.length()));
        return Long.valueOf(StrUtil.reverse(s1 + s2));
    }

    public static RoomType getRoomType(Long roomId) {
        return String.valueOf(roomId).length() == GROUPID_LEN
                ? RoomType.GROUP
                : RoomType.FRIEND;
    }
}
