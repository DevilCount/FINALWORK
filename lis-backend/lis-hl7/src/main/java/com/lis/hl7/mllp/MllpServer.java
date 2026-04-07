package com.lis.hl7.mllp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MllpServer {

    private final int port;
    private final String host;
    private final MllpMessageHandler messageHandler;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    private volatile boolean running = false;

    public MllpServer(String host, int port, MllpMessageHandler messageHandler) {
        this.host = host;
        this.port = port;
        this.messageHandler = messageHandler;
    }

    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    .addLast(new MllpDecoder())
                                    .addLast(new MllpEncoder())
                                    .addLast(new MllpServerHandler(messageHandler));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            channelFuture = bootstrap.bind(host, port).sync();
            running = true;
            log.info("MLLP Server started on {}:{}", host, port);
        } catch (Exception e) {
            log.error("Failed to start MLLP Server on {}:{}", host, port, e);
            shutdown();
            throw e;
        }
    }

    public void shutdown() {
        running = false;
        if (channelFuture != null) {
            channelFuture.channel().close().awaitUninterruptibly();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        log.info("MLLP Server stopped on {}:{}", host, port);
    }

    public boolean isRunning() {
        return running;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }
}
