package com.ttit.syslog.provider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ttit.syslog.provider.dao")
@EnableDubboConfiguration
@SpringBootApplication
public class SyslogProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyslogProviderApplication.class, args);
    }

}
