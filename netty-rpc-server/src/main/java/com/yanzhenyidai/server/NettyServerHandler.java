package com.yanzhenyidai.server;

import com.alibaba.fastjson.JSON;
import com.yanzhenyidai.common.http.Request;
import com.yanzhenyidai.common.http.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author frank
 * @version 1.0
 * @date 2020-04-08 17:53
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;

        System.out.println("Client Data:" + JSON.toJSONString(request));

        Response response = new Response();
        response.setRequestId(request.getRequestId());
        response.setResult("Hello Client !");

        ctx.writeAndFlush(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
