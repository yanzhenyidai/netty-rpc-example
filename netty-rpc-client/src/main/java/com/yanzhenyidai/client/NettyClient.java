package com.yanzhenyidai.client;

import com.alibaba.fastjson.JSON;
import com.yanzhenyidai.common.http.Request;
import com.yanzhenyidai.common.http.Response;
import com.yanzhenyidai.common.serialize.protostuff.RpcDecoder;
import com.yanzhenyidai.common.serialize.protostuff.RpcEncoder;
import com.yanzhenyidai.common.zookeeper.Discover;
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
public class NettyClient extends SimpleChannelInboundHandler<Response> {

//    private final String ip;
//    private final int port;
//
//    public NettyClient(String ip, int port) {
//        this.ip = ip;
//        this.port = port;
//    }

    private Response response;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        this.response = response;
    }

    public Response client(Request request) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {

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
                    pipeline.addLast(NettyClient.this);
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);


            String[] discover = new Discover().discover(request.getInterfaceName()).split(":");

            // 连接 RPC 服务器
            ChannelFuture future = bootstrap.connect(discover[0], Integer.valueOf(discover[1])).sync();

            // 写入 RPC 请求数据并关闭连接
            Channel channel = future.channel();

            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();

            return response;
        } finally {
            group.shutdownGracefully();
        }
    }

//    public static void main(String[] args) throws Exception {
//        Request request = new Request();
//        request.setRequestId(UUID.randomUUID().toString());
//        request.setParameter("Hello Server !");
//        System.out.println(JSON.toJSONString(new NettyClient("127.0.0.1", 30000).client(request)));
//    }
}