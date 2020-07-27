package com.aswqazx.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author OMNIS
 */
@Configuration
public class SwaggerConfig {

    //http://ip:port/swagger-ui/index.html

    public static final String TOKEN = "Access-Token";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .pathMapping("/")
                // 选择那些路径和api会生成document
                .select()
                // 对所有api进行监控
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.aswqazx.server.rest"))
                .build()
                //添加全局token
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("这是Swagger3生成的接口文档")
                .version("v1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey(TOKEN, TOKEN, "header");
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(apiKey);
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
        List<SecurityContext> securityContextList = new ArrayList<>();
        securityContextList.add(securityContext);
        return securityContextList;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        SecurityReference securityReference = new SecurityReference(TOKEN, authorizationScopes);
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(securityReference);
        return securityReferenceList;
    }

}
