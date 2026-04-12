package com.lis.equipment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lis.equipment.mapper")
@ComponentScan(basePackages = "com.lis")
public class EquipmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentApplication.class, args);
    }
}
