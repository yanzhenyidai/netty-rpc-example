package com.yanzhenyidai.client;

import com.alibaba.fastjson.JSON;
import com.yanzhenyidai.common.http.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-09 15:29
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Response> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        System.out.println("服务端返回消息:" + JSON.toJSONString(response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
