package com.savings.account;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class SavingsAccountMsApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(SavingsAccountMsApplication.class, args);
	}

}
