package com.yanzhenyidai.server;

import com.yanzhenyidai.common.http.Request;
import com.yanzhenyidai.common.http.Response;
import com.yanzhenyidai.common.serialize.protostuff.RpcDecoder;
import com.yanzhenyidai.common.serialize.protostuff.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author frank
 * @version 1.0
 * @date 2020-04-09 11:28
 */
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args) throws Exception {

//
//        EventLoopGroup bossGroup = new NioEventLoopGroup(); //bossGroup就是parentGroup，是负责处理TCP/IP连接的
//        EventLoopGroup workerGroup = new NioEventLoopGroup(); //workerGroup就是childGroup,是负责处理Channel(通道)的I/O事件
//
//        ServerBootstrap sb = new ServerBootstrap();
//        sb.group(bossGroup, workerGroup)
//                .channel(NioServerSocketChannel.class)
//                .option(ChannelOption.SO_BACKLOG, 128) //初始化服务端可连接队列,指定了队列的大小128
//                .childOption(ChannelOption.SO_KEEPALIVE, true) //保持长连接
//                .childHandler(new ChannelInitializer<SocketChannel>() {  // 绑定客户端连接时候触发操作
//                    @Override
//                    protected void initChannel(SocketChannel sh) throws Exception {
//                        sh.pipeline()
//                                .addLast(new RpcDecoder(Request.class)) //解码request
//                                .addLast(new RpcEncoder(Response.class)) //编码response
//                                .addLast(new NettyServerHandler()); //使用ServerHandler类来处理接收到的消息
//                    }
//                });
//        //绑定监听端口，调用sync同步阻塞方法等待绑定操作完成，完成后返回ChannelFuture类似于JDK中Future
//        ChannelFuture future = sb.bind(30000).sync();
//
//        if (future.isSuccess()) {
//            System.out.println("服务端启动成功");
//        } else {
//            System.out.println("服务端启动失败");
//            future.cause().printStackTrace();
//            bossGroup.shutdownGracefully(); //关闭线程组
//            workerGroup.shutdownGracefully();
//        }
//
//        //成功绑定到端口之后,给channel增加一个 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
//        future.channel().closeFuture().sync();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new RpcDecoder(Request.class))
                                .addLast(new RpcEncoder(Response.class))
                                .addLast(new NettyServerHandler());
                    }
                });

        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future = serverBootstrap.bind("127.0.0.1", 30000).sync();

        System.out.println(future.isSuccess());

        future.channel().closeFuture().sync();
    }
}
