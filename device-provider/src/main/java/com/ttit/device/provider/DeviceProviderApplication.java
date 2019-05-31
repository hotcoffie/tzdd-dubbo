package com.ttit.device.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubboConfiguration
@SpringBootApplication
public class DeviceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceProviderApplication.class, args);
    }

}
