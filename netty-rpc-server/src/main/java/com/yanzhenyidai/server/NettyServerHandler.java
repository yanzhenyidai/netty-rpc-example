package com.yanzhenyidai.server;

import com.alibaba.fastjson.JSON;
import com.yanzhenyidai.common.http.Request;
import com.yanzhenyidai.common.http.Response;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2020-04-08 17:53
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private final Map<String, Object> handle;

    public NettyServerHandler(Map<String, Object> handle) {
        this.handle = handle;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;

        logger.info("rquest data:" + JSON.toJSONString(request));

        Response response = new Response();

        // jdk 反射调用
        Object bean = handle.get(request.getInterfaceName());

        Class<?> cls = bean.getClass();

        Method method = cls.getMethod(request.getMethodName(), request.getParameterTypes());
        method.setAccessible(true);
        Object result = method.invoke(bean, request.getParameter());

        response.setRequestId(request.getRequestId());
        response.setResult(result);

        // client接收到信息后主动关闭掉连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
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
