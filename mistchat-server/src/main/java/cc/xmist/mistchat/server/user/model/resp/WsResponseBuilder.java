package cc.xmist.mistchat.server.user.model.resp;

import cc.xmist.mistchat.server.user.entity.User;
import cc.xmist.mistchat.server.user.model.enums.WSResponseTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class WsResponseBuilder {
    public static WSBaseResponse loginSuccess(User user, String token) {
        WSLoginSuccess wsLoginSuccess = WSLoginSuccess.builder()
                .uid(user.getId())
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token).build();

        return WSBaseResponse.builder()
                .type(WSResponseTypeEnum.LOGIN_SUCCESS.getType())
                .data(wsLoginSuccess)
                .build();
    }

    public static WSBaseResponse failed(String data) {
        return WSBaseResponse.builder()
                .type(WSResponseTypeEnum.SERVER_FAILED.getType())
                .data(data)
                .build();
    }

        public static WSBaseResponse<Object> invalidToken() {
        return WSBaseResponse
                .builder()
                .type(WSResponseTypeEnum.INVALIDATE_TOKEN.getType())
                .build();
    }
}
