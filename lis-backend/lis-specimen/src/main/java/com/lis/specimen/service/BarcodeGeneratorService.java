package com.lis.specimen.service;

public interface BarcodeGeneratorService {

    String generateBarcode();

    String generateSpecimenNo();

    String generateBarcodeWithPrefix(String prefix);
}
