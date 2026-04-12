package com.lis.hl7;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lis")
@ComponentScan(basePackages = "com.lis")
@MapperScan("com.lis.hl7.mapper")
public class Hl7Application {

    public static void main(String[] args) {
        SpringApplication.run(Hl7Application.class, args);
    }
}
