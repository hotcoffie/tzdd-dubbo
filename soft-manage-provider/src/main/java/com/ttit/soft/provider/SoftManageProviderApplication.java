package com.ttit.soft.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ttit.soft.provider.dao")
@EnableDubboConfiguration
@SpringBootApplication
public class SoftManageProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftManageProviderApplication.class, args);
    }

}
