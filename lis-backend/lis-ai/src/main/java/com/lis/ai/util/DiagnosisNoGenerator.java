package com.lis.ai.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class DiagnosisNoGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final AtomicLong SEQUENCE = new AtomicLong(0);

    public static String generate(String prefix) {
        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DATE_FORMATTER);
        long seq = SEQUENCE.getAndIncrement() % 10000;
        return String.format("%s%s%04d", prefix, dateStr, seq);
    }
}
