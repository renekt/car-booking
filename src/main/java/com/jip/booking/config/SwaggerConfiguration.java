package com.jip.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jip
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String SCAN_BASE_PACKAGE = "com.jip.booking";

    private static final String VERSION = "1.0.0";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Booking Server API").description("Booking Server API").termsOfServiceUrl("")
                                   .version(VERSION).build();
    }

    @Bean
    public Docket apiDocket() {

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("request token").modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2).groupName("RestfulApi").select()
                                                      .apis(RequestHandlerSelectors.basePackage(SCAN_BASE_PACKAGE))
                                                      .paths(PathSelectors.any()).build().ignoredParameterTypes()
                                                      .apiInfo(apiInfo()).globalOperationParameters(pars).enable(true);
    }

}
