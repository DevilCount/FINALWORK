package com.lis.hl7.mllp;

public interface MllpMessageHandler {

    String handleMessage(String message, String clientIp) throws Exception;

    void onConnect(String clientIp);

    void onDisconnect(String clientIp);

    void onError(String clientIp, String error);
}
