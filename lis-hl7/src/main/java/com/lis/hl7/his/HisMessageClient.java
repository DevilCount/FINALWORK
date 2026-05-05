package com.lis.hl7.his;

import ca.uhn.hl7v2.HL7Exception;
import com.lis.hl7.builder.Hl7MessageBuilder;
import com.lis.hl7.entity.InterfaceConfigDO;
import com.lis.hl7.parser.Hl7MessageParser;
import com.lis.hl7.service.InterfaceConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

@Slf4j
@Component
@RequiredArgsConstructor
public class HisMessageClient {

    private final Hl7MessageParser messageParser;
    private final Hl7MessageBuilder messageBuilder;
    private final InterfaceConfigService interfaceConfigService;

    private static final byte START_BLOCK = 0x0B;
    private static final byte END_BLOCK = 0x1C;
    private static final byte CARRIAGE_RETURN = 0x0D;

    public String sendMessage(String interfaceCode, String message) throws Exception {
        InterfaceConfigDO config = interfaceConfigService.getByCode(interfaceCode);
        if (config == null) {
            throw new IllegalArgumentException("Interface config not found: " + interfaceCode);
        }

        return sendMessage(config, message);
    }

    public String sendMessage(InterfaceConfigDO config, String message) throws Exception {
        String host = config.getHost();
        int port = config.getPort();
        int connectionTimeout = config.getConnectionTimeout() != null ? config.getConnectionTimeout() : 30000;
        int readTimeout = config.getReadTimeout() != null ? config.getReadTimeout() : 60000;
        String charset = config.getCharset() != null ? config.getCharset() : "UTF-8";

        return sendMllpMessage(host, port, message, connectionTimeout, readTimeout, charset);
    }

    public String sendMllpMessage(String host, int port, String message, 
                                   int connectionTimeout, int readTimeout, String charset) throws Exception {
        log.info("Sending HL7 message to {}:{}", host, port);

        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(readTimeout);
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);

            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();

            byte[] messageBytes = message.getBytes(Charset.forName(charset));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(START_BLOCK);
            baos.write(messageBytes);
            baos.write(END_BLOCK);
            baos.write(CARRIAGE_RETURN);

            outputStream.write(baos.toByteArray());
            outputStream.flush();

            log.info("HL7 message sent, waiting for response...");

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            boolean startFound = false;
            boolean endFound = false;

            while (!endFound) {
                int b = inputStream.read();
                if (b == -1) {
                    break;
                }

                if (b == START_BLOCK) {
                    startFound = true;
                    continue;
                }

                if (b == END_BLOCK) {
                    endFound = true;
                    int next = inputStream.read();
                    if (next != CARRIAGE_RETURN && next != -1) {
                        responseBuffer.write(b);
                        responseBuffer.write(next);
                    }
                    break;
                }

                if (startFound) {
                    responseBuffer.write(b);
                }
            }

            String response = responseBuffer.toString(charset);
            log.info("Received ACK response from HIS");

            return response;

        } catch (Exception e) {
            log.error("Error sending HL7 message to {}:{}", host, port, e);
            throw e;
        } finally {
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                log.warn("Error closing connection", e);
            }
        }
    }

    public String sendAndValidate(String interfaceCode, String message) throws Exception {
        String response = sendMessage(interfaceCode, message);

        String ackStatus = extractAckStatus(response);
        if (!"AA".equals(ackStatus)) {
            String ackMessage = extractAckMessage(response);
            throw new HL7Exception("HIS returned error ACK: " + ackStatus + " - " + ackMessage);
        }

        return response;
    }

    public String sendAndValidate(InterfaceConfigDO config, String message) throws Exception {
        String response = sendMessage(config, message);

        String ackStatus = extractAckStatus(response);
        if (!"AA".equals(ackStatus)) {
            String ackMessage = extractAckMessage(response);
            throw new HL7Exception("HIS returned error ACK: " + ackStatus + " - " + ackMessage);
        }

        return response;
    }

    private String extractAckStatus(String ackMessage) {
        try {
            String[] lines = ackMessage.split("\r|\n");
            for (String line : lines) {
                if (line.startsWith("MSA|")) {
                    String[] fields = line.split("\\|");
                    if (fields.length >= 2) {
                        return fields[1];
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to extract ACK status", e);
        }
        return null;
    }

    private String extractAckMessage(String ackMessage) {
        try {
            String[] lines = ackMessage.split("\r|\n");
            for (String line : lines) {
                if (line.startsWith("MSA|")) {
                    String[] fields = line.split("\\|");
                    if (fields.length >= 4) {
                        return fields[3];
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to extract ACK message", e);
        }
        return null;
    }

    public boolean testConnection(String interfaceCode) {
        InterfaceConfigDO config = interfaceConfigService.getByCode(interfaceCode);
        if (config == null) {
            return false;
        }

        return testConnection(config.getHost(), config.getPort(), 
                config.getConnectionTimeout() != null ? config.getConnectionTimeout() : 5000);
    }

    public boolean testConnection(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new java.net.InetSocketAddress(host, port), timeout);
            return true;
        } catch (Exception e) {
            log.warn("Connection test failed for {}:{}", host, port, e);
            return false;
        }
    }
}
