package cc.xmist.mistchat.server.common.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

public class IdUtil {

    /**
     * 生成 7 位群聊 id
     * @return
     */
    public static Long genGroupId() {
        String time = String.valueOf(System.currentTimeMillis());
        String s1 = time.substring(time.length() - 3);
        String s2 = String.valueOf(RandomUtil.randomString(4));
        return Long.valueOf(StrUtil.reverse(s1 + s2));
    }
}
