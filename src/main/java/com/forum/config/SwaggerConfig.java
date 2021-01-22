package com.forum.config;

import com.google.common.collect.Lists;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//@Configuration
//@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        Docket docket =  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .securitySchemes(Lists.newArrayList(apiKey()))
                ;  
        return docket;                                       
    }

    private ApiKey apiKey() {
    	return new ApiKey("Authorization", "Authorization", "header");
    	
    }
}