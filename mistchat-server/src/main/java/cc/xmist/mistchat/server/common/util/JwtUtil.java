package cc.xmist.mistchat.server.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

public class JwtUtil {

    private String secret;

    public JwtUtil(String secret) {
        this.secret = secret;
    }

    public static final String UID = "uid";
    public static final String CREATE_TIME = "createTime";

    public String createToken(Integer uid) {
        String token = JWT.create()
                .withClaim(UID, uid)
//                .withClaim("createTime", new Date()) TODO 暂时是无期限
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    public Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    public Integer getUid(String token) {
        Map<String, Claim> map = verifyToken(token);
        return map.get("uid").asInt();
    }
}
