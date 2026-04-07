package com.lis.hl7.mllp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class MllpServerHandler extends SimpleChannelInboundHandler<String> {

    private final MllpMessageHandler messageHandler;

    public MllpServerHandler(MllpMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("Received HL7 message from {}", ctx.channel().remoteAddress());

        try {
            String response = messageHandler.handleMessage(msg, getClientIp(ctx));

            if (response != null && !response.isEmpty()) {
                ctx.writeAndFlush(response);
                log.info("Sent ACK response to {}", ctx.channel().remoteAddress());
            }
        } catch (Exception e) {
            log.error("Error processing HL7 message", e);
            ctx.close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client connected: {}", ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client disconnected: {}", ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception in MLLP handler: {}", cause.getMessage(), cause);
        ctx.close();
    }

    private String getClientIp(ChannelHandlerContext ctx) {
        if (ctx.channel().remoteAddress() instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            return inetSocketAddress.getAddress().getHostAddress();
        }
        return "unknown";
    }
}
