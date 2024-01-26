package cc.xmist.mistchat.server.chat.message;

import cc.xmist.mistchat.server.chat.model.enums.MessageType;

import java.util.HashMap;
import java.util.Map;

public class MessageHandleFactory {
    private static Map<MessageType, AbstractMessageHandler> MSG_STRATEGY_MAP = new HashMap<>();

    public static void registerHandle(MessageType type, AbstractMessageHandler messageHandle) {
        MSG_STRATEGY_MAP.put(type, messageHandle);
    }


    public static AbstractMessageHandler getHandle(MessageType type) {
        return MSG_STRATEGY_MAP.get(type);
    }
}
