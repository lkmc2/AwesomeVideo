package com.lin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lkmc2
 * @date 2018/9/29
 * @description Swagger2配置
 * 启动页面：http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 创建RestApi
     */
    @Bean
    public Docket createRestApi() {
        // 为Swagger添加header参数可供输入
//        ParameterBuilder userTokenHeader = new ParameterBuilder();
//        ParameterBuilder userIdHeader = new ParameterBuilder();

        List<Parameter> paramList = new ArrayList<>();

//        userTokenHeader
//                .name("headerUserToken")
//                .description("userToken")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false);
//
//        userIdHeader
//                .name("headerUserId")
//                .description("userId")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false);
//
//        paramList.add(userTokenHeader.build());
//        paramList.add(userIdHeader.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lin.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(paramList);
    }

    /**
     * 设置Api信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("倾心短视频后端API接口文档")
                .contact(new Contact("lkmc2", "https://github.com/lkmc2", "lkmc2@163.com"))
                .description("欢迎访问短视频接口文档")
                .version("1.0")
                .build();
    }

}
