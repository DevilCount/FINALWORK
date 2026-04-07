package com.lis.hl7.mllp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class MllpDecoder extends ByteToMessageDecoder {

    private static final byte START_BLOCK = 0x0B;
    private static final byte END_BLOCK = 0x1C;
    private static final byte CARRIAGE_RETURN = 0x0D;

    private final Charset charset;

    public MllpDecoder() {
        this.charset = Charset.forName("UTF-8");
    }

    public MllpDecoder(String charsetName) {
        this.charset = Charset.forName(charsetName);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int startIndex = -1;
        int endIndex = -1;

        int readableBytes = in.readableBytes();
        for (int i = in.readerIndex(); i < in.readerIndex() + readableBytes; i++) {
            byte b = in.getByte(i);
            if (b == START_BLOCK && startIndex == -1) {
                startIndex = i;
            } else if (b == END_BLOCK && startIndex != -1) {
                endIndex = i;
                break;
            }
        }

        if (startIndex != -1 && endIndex != -1) {
            int messageStart = startIndex + 1;
            int messageLength = endIndex - messageStart;

            if (in.getByte(endIndex + 1) == CARRIAGE_RETURN) {
                in.skipBytes(endIndex - in.readerIndex() + 2);
            } else {
                in.skipBytes(endIndex - in.readerIndex() + 1);
            }

            byte[] messageBytes = new byte[messageLength];
            in.readBytes(messageBytes);

            String message = new String(messageBytes, charset);
            log.debug("Received MLLP message: {}", message.length() > 200 ? message.substring(0, 200) + "..." : message);

            out.add(message);
        }
    }
}
