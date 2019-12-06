package com.savings.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.savings.account.model.HeadLineEntity;
import com.savings.account.model.SavingEntity;
import com.savings.account.service.SavingServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class RestControllerSaving {

	@Autowired
	SavingServiceImpl savingImpl;
	
	@GetMapping
	Flux<SavingEntity> getSavings(){
		return savingImpl.allSaving();
	}
	
	@PostMapping
	Mono<SavingEntity> postSaving(@RequestBody SavingEntity saving){
	/*	WebClient client = WebClient.create("http://localhost:8084/api");
		Flux<HeadLineEntity> head =  client.post().uri("/getCustomers").body(saving.getHead(), HeadLineEntity.class)
				.retrieve().bodyToFlux(HeadLineEntity.class);*/
		
		return savingImpl.saveSaving(saving);
	}
}
