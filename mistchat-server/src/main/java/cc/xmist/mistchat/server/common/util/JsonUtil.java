package cc.xmist.mistchat.server.common.util;

import com.google.gson.Gson;

public class JsonUtil {

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T toObj(String str, Class<T> clazz) {
        return new Gson().fromJson(str, clazz);
    }
}
