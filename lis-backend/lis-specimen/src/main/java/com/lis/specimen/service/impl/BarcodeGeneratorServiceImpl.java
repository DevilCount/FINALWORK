package com.lis.specimen.service.impl;

import com.lis.specimen.service.BarcodeGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarcodeGeneratorServiceImpl implements BarcodeGeneratorService {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String BARCODE_KEY_PREFIX = "lis:specimen:barcode:";
    private static final String SPECIMEN_NO_KEY_PREFIX = "lis:specimen:no:";
    private static final String DEFAULT_PREFIX = "SP";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final int SEQ_LENGTH = 6;

    @Override
    public String generateBarcode() {
        return generateBarcodeWithPrefix(DEFAULT_PREFIX);
    }

    @Override
    public String generateSpecimenNo() {
        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String key = SPECIMEN_NO_KEY_PREFIX + dateStr;
        Long seq = stringRedisTemplate.opsForValue().increment(key);
        stringRedisTemplate.expire(key, 2, TimeUnit.DAYS);
        String seqStr = String.format("%0" + SEQ_LENGTH + "d", seq);
        return DEFAULT_PREFIX + dateStr + seqStr;
    }

    @Override
    public String generateBarcodeWithPrefix(String prefix) {
        String dateStr = LocalDate.now().format(DATE_FORMATTER);
        String key = BARCODE_KEY_PREFIX + prefix + ":" + dateStr;
        Long seq = stringRedisTemplate.opsForValue().increment(key);
        stringRedisTemplate.expire(key, 2, TimeUnit.DAYS);
        String seqStr = String.format("%0" + SEQ_LENGTH + "d", seq);
        return prefix + dateStr + seqStr;
    }
}
