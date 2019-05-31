package com.ttit.soft.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubboConfiguration
@SpringBootApplication
public class SoftManageProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftManageProviderApplication.class, args);
    }

}
