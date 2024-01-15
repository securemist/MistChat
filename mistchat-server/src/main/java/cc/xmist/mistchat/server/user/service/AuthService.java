package cc.xmist.mistchat.server.user.service;

import cc.xmist.mistchat.server.common.constant.RedisKey;
import cc.xmist.mistchat.server.common.jwt.JwtUtil;
import cc.xmist.mistchat.server.common.util.RedisUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.val;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    private static final long TOKEN_EXPIRE_DAYS = 3;
    private static final long TOKEN_REFRESH_DAYS_LIMIT = 1;

    @Resource
    private JwtUtil jwtUtil;


    @Async
    public void refreshToken(String token) {
        Long uid = verify(token);
        if (uid == null) {
            return;
        }

        Long expireDays = RedisUtil.getExpire(formatTokenKey(uid), TimeUnit.DAYS);
        // key 不存在
        if (expireDays == -2) {
            return;
        }

        // 如果过期时间小于一天就更新
        if (expireDays < TOKEN_REFRESH_DAYS_LIMIT) {
            RedisUtil.set(formatTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }


    /**
     * 用户登陆，将uid和token存入redis
     *
     * @param uid
     * @return
     */
    public String login(Long uid) {
        String token = jwtUtil.createToken(uid);
        RedisUtil.set(formatTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }

    /**
     * 校验用户token，校验成功返回uid
     *
     * @param token
     * @return
     */
    public Long verify(String token) {
        Long uid = jwtUtil.getUid(token);
        if (uid == null) {
            return null;
        }
        // 如果redis中存的token过期，也返回null
        String oldToken = RedisUtil.get(formatTokenKey(uid));
        if (StrUtil.isBlank(oldToken)) {
            return null;
        }

        return oldToken.equals(token) ? uid : null;
    }

    private static String formatTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }
}
