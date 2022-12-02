package com.example.demoapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DemoapigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoapigatewayApplication.class, args);
	}

}
