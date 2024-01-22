package cc.xmist.mistchat.server.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtUtil {

    @Value("${mistchat.jwt.secret}")
    private String secret;

    public static final String UID = "uid";
    public static final String CREATE_TIME = "createTime";

    public String createToken(Long uid) {
        String token = JWT.create()
                .withClaim(UID, uid)
//                .withClaim("createTime", new Date())
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    public Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    public Long getUid(String token) {
        Map<String, Claim> map = verifyToken(token);
        return map.get("uid").asLong();
    }
}
