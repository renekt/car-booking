package com.jip.booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jip
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;

    public WebConfiguration(AuthInterceptor authInterceptor) {this.authInterceptor = authInterceptor;}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-resources").excludePathPatterns("/*/api-docs")
                .excludePathPatterns("/swagger-ui.html/**").excludePathPatterns("/v2/api-docs");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
