package com.ttit.attachment.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ttit.attachment.provider.dao")
@EnableDubboConfiguration
@SpringBootApplication
public class AttachmentProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachmentProviderApplication.class, args);
    }

}
