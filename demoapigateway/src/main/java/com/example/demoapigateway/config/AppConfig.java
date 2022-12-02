package com.example.demoapigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p->p
                        .path("/user/v1/**")
                        .uri("http://localhost:8092/")
                ).route(p->p
                        .path("/userTrackapp/user/**")
                        .uri("http://localhost:8083/")
                ).build();
    }
}
