package cc.xmist.websocket;

import cc.xmist.websocket.enums.WSRequestType;
import cc.xmist.websocket.req.WSBaseRequest;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        WSBaseRequest wsRequest = new Gson().fromJson(msg.text(), WSBaseRequest.class);
        // 收到消息，处理业务
        String test = msg.text();
        switch (WSRequestType.of(wsRequest.getType())) {
            case LOGIN -> {
                log.info("请求登陆");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("这是你的二维码"));
            }
            case AUTHORIZE -> {

            }
            case HEARTBEAT -> {

            }
        }
    }
}
