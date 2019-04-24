package com.hcl.cloud.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfiguration class to generate the API documentation.
 * @author baghelp
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @SuppressWarnings("deprecation")
    public static final ApiInfo DEFAULT_CUSTOM_INFO = new ApiInfo("Cart Microservice API Documents",
            "Cart Microservice API Documents", "1.0.0", "urn:tos", "baghelp@hcl.com|kumar_sanjay@hcl.com", "Cloud Foundry",
            "http://www.pivotal.org/licenses/LICENSE-2.0");

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_CUSTOM_INFO);
    }

}