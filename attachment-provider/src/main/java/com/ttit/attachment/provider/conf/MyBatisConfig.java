package com.ttit.attachment.provider.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: springboot集成mybatis的基本入口
 *
 * @author 谢宇
 * Date: 2019/4/11 9:52
 */
@Configuration
@MapperScan(basePackages = {"com.ttit.attachment.provider.dao"})
@EnableTransactionManagement
public class MyBatisConfig {

}