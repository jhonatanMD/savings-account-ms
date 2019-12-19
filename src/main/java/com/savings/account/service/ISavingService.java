package com.savings.account.service;

import java.util.Date;

import com.savings.account.model.SavingEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISavingService {

	Flux<SavingEntity> allSaving();
	Mono<SavingEntity> saveSaving(SavingEntity saving);
	
	Mono<SavingEntity>  findByNumAcc(String dni);
	
	Mono<SavingEntity> updSaving(SavingEntity saving);
	Mono<SavingEntity> transactiosSaving(String numAcc,String tipo, Double cash);
	Mono<Void> dltSaving(String id);
	
	Flux<SavingEntity> findBytitularesByDoc(SavingEntity saving);
	Flux<SavingEntity> findByDoc(String doc);
	
	Mono<SavingEntity> payCreditCard(String numAcc,String numCard,Double cash);
	
	Flux<SavingEntity> findByDates (String from, String until, String numAcc); 
	
}