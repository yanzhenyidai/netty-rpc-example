package com.yanzhenyidai.client;

import com.yanzhenyidai.common.http.Request;
import com.yanzhenyidai.common.http.Response;
import com.yanzhenyidai.common.serialize.protostuff.RpcDecoder;
import com.yanzhenyidai.common.serialize.protostuff.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * @author frank
 * @version 1.0
 * @date 2020-05-09 15:33
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

//        final EventLoopGroup group = new NioEventLoopGroup();
//
//        Bootstrap b = new Bootstrap();
//        b.group(group).channel(NioSocketChannel.class)  // 使用NioSocketChannel来作为连接用的channel类
//                .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
//                    @Override
//                    public void initChannel(SocketChannel ch) throws Exception {
//                        System.out.println("正在连接中...");
//                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast(new RpcEncoder(Request.class)); //编码request
//                        pipeline.addLast(new RpcDecoder(Response.class)); //解码response
//                        pipeline.addLast(new NettyClientHandler()); //客户端处理类
//
//                    }
//                });
//        //发起异步连接请求，绑定连接端口和host信息
//        final ChannelFuture future = b.connect("127.0.0.1", 30000).sync();
//
//        future.addListener(new ChannelFutureListener() {
//
//            @Override
//            public void operationComplete(ChannelFuture arg0) throws Exception {
//                if (future.isSuccess()) {
//                    System.out.println("连接服务器成功");
//
//                } else {
//                    System.out.println("连接服务器失败");
//                    future.cause().printStackTrace();
//                    group.shutdownGracefully(); //关闭线程组
//                }
//            }
//        });
//
//        Channel channel = future.channel();
//
//        Request request = new Request();
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setParameter("client.message");
//        //channel对象可保存在map中，供其它地方发送消息
//        channel.writeAndFlush(request);
//
//        channel.closeFuture().sync();

        EventLoopGroup group = new NioEventLoopGroup();

        // 创建并初始化 Netty 客户端 Bootstrap 对象
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();

                pipeline.addLast(new RpcDecoder(Response.class));
                pipeline.addLast(new RpcEncoder(Request.class));
                pipeline.addLast(new NettyClientHandler());
            }
        });
        bootstrap.option(ChannelOption.TCP_NODELAY, true);

        // 连接 RPC 服务器
        ChannelFuture future = bootstrap.connect("127.0.0.1", 30000).sync();
        // 写入 RPC 请求数据并关闭连接
        Channel channel = future.channel();

        Request request = new Request();
        request.setRequestId(UUID.randomUUID().toString());
        request.setParameter("Hello Server! ");

        channel.writeAndFlush(request);

        channel.closeFuture().sync();
    }
}