package com.yanzhenyidai.server;

import com.yanzhenyidai.common.http.Request;
import com.yanzhenyidai.common.http.Response;
import com.yanzhenyidai.common.serialize.protostuff.RpcDecoder;
import com.yanzhenyidai.common.serialize.protostuff.RpcEncoder;
import com.yanzhenyidai.common.zookeeper.Register;
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

import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2020-04-09 11:28
 */
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private String ip;
    private int port;

    public NettyServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void server(Map<String, Object> params) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

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
                                    .addLast(new NettyServerHandler(params));
                        }
                    });

            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = serverBootstrap.bind(ip, port).sync();

            if (future.isSuccess()) {
                for (String key : params.keySet()) {
                    new Register().register(key, ip + ":" + port);

                    logger.info("\n\n register success, node: {}, data: {}", key, ip + ":" + port);
                }
            }

            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
//
//    public static void main(String[] args) throws Exception {
//        new NettyServer("127.0.0.1", 20000).server(null);
//    }
}
