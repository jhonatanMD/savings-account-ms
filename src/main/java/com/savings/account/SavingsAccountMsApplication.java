package com.savings.account;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@EnableEurekaClient
@SpringBootApplication
public class SavingsAccountMsApplication {

	
	
	@Bean
	WebClient webClient() {
		return WebClient.create("http://localhost:8082/api");
			//	.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(SavingsAccountMsApplication.class, args);
	}

}
