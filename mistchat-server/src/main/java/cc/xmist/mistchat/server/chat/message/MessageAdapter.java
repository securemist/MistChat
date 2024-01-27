package cc.xmist.mistchat.server.chat.message;

import cc.xmist.mistchat.server.chat.model.dao.RoomDao;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.resp.ChatMessageResponse;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;

public class MessageAdapter {

    public static ChatMessageResponse buildResponse(Long fromUid, Message message) {
        ChatMessageResponse response = new ChatMessageResponse();
        response.setFromUser(ChatMessageResponse.UserInfo
                .builder()
                .uid(fromUid)
                .build());

        response.setMessage(ChatMessageResponse.Message
                .builder()
                .body(message.getExtra())
                .roomId(message.getRoomId())
                .type(message.getType())
                .sendTime(message.getCreateTime())
                .uid(fromUid)
                .build());
        return response;
    }
}
