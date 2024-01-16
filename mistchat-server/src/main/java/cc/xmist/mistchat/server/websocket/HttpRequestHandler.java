package cc.xmist.mistchat.server.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;


public class HttpRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            // 判断是不是http请求，websocket的第一个请求为http请求，new WebSocket(ws://127.0.0.1:8080?token=123)
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri().toString());
            // 获取token
            String token = urlBuilder.getQuery().get("token").toString();
            if (StrUtil.isNotBlank(token)) {
                NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, token);
            }
            // 删除请求url中的token参数，否则webSocket连接失败
            request.setUri(urlBuilder.getPath().toString());
//            ctx.pipeline().remove(this);
//            ctx.fireChannelRead(request);
        }
        ctx.fireChannelRead(msg);

    }
}
