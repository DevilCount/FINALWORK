package com.lis.hl7.mllp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class MllpEncoder extends MessageToByteEncoder<String> {

    private static final byte START_BLOCK = 0x0B;
    private static final byte END_BLOCK = 0x1C;
    private static final byte CARRIAGE_RETURN = 0x0D;

    private final Charset charset;

    public MllpEncoder() {
        this.charset = Charset.forName("UTF-8");
    }

    public MllpEncoder(String charsetName) {
        this.charset = Charset.forName(charsetName);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        byte[] messageBytes = msg.getBytes(charset);

        out.writeByte(START_BLOCK);
        out.writeBytes(messageBytes);
        out.writeByte(END_BLOCK);
        out.writeByte(CARRIAGE_RETURN);

        log.debug("Sent MLLP message: {}", msg.length() > 200 ? msg.substring(0, 200) + "..." : msg);
    }
}
