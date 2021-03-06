package com.ttit.syslog.web.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description:配置swagger-ui
 *
 * @author 小谢
 * Date: 2019/5/219:09
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // 定义分隔符,配置Swagger多包
    private static final String SPLITOR = ";";

    /**
     * 以下是使用swagger2 生成api文档
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ttit.syslog.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息函数
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("投注终端管理系统")
                .description("提供系统全部可用API的接口测试")
                .version("1.0")
                .build();
    }
}
