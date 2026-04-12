package com.lis.specimen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lis.specimen.feign")
@ComponentScan(basePackages = "com.lis")
public class SpecimenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecimenApplication.class, args);
    }
}
