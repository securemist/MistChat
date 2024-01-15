package cc.xmist.mistchat.server.common.constant;

public class RedisKey {
    private static final String BASE_KEY = "mistchat:chat";
    public static final String USER_TOKEN_STRING = "user:token:uid_%d";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }
}
