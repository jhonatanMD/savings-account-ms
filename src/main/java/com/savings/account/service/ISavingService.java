package com.savings.account.service;

import com.savings.account.model.SavingEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISavingService {

	Flux<SavingEntity> allSaving();
	Mono<SavingEntity> saveSaving(SavingEntity saving);
	Mono<SavingEntity> updSaving(SavingEntity saving);
	Mono<Void> dltSaving(String id);
	
	
}