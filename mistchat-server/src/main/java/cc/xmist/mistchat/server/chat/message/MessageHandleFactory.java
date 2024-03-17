package cc.xmist.mistchat.server.chat.message;

import cc.xmist.mistchat.server.common.enums.MessageType;

import java.util.HashMap;
import java.util.Map;

public class MessageHandleFactory {
    private static Map<MessageType, AbstractMsgHandler> MSG_STRATEGY_MAP = new HashMap<>();

    public static void registerHandle(MessageType type, AbstractMsgHandler messageHandle) {
        MSG_STRATEGY_MAP.put(type, messageHandle);
    }


    public static AbstractMsgHandler getHandle(MessageType type) {
        return MSG_STRATEGY_MAP.get(type);
    }
}
