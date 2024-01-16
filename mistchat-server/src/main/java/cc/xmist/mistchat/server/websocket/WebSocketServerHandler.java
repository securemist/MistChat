package cc.xmist.mistchat.server.websocket;

import cc.xmist.mistchat.server.common.util.JsonUtil;
import cc.xmist.mistchat.server.user.model.req.WSBaseRequest;
import cc.xmist.mistchat.server.user.model.enums.WSRequestTypeEnum;
import cc.xmist.mistchat.server.user.service.WebSocketService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static WebSocketService webSocketService;

    static {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        WSBaseRequest wsRequest = JsonUtil.toObj(msg.text(), WSBaseRequest.class);
        // 收到消息，处理业务
        String text = msg.text();
        switch (WSRequestTypeEnum.of(wsRequest.getType())) {
            case LOGIN -> {
                webSocketService.handleLoginReq(ctx.channel(), wsRequest.getData());
            }
            case AUTHORIZE -> {
                webSocketService.handleAuthorize(ctx.channel(), wsRequest.getData());
            }
            case HEARTBEAT -> {

            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if (StrUtil.isNotBlank(token)) {
                webSocketService.handleAuthorize(ctx.channel(), token);
            }
            log.info("握手完成");
        }

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("用户下线");
                webSocketService.userOffLine(ctx.channel());
                ctx.channel().close();
            }
        }
    }

    /**
     * 用户下线
     *
     * @param channel
     */
    private void userOffLine(Channel channel) {

    }
}
