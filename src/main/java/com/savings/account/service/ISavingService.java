package com.savings.account.service;

import com.savings.account.model.EntityTransaction;
import com.savings.account.model.SavingEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISavingService {

  Flux<SavingEntity> allSaving();
  
  Mono<SavingEntity> saveSaving(SavingEntity saving);

  Mono<SavingEntity> findByNumAcc(String dni);

  Mono<SavingEntity> updSaving(SavingEntity saving);
  
  Mono<EntityTransaction> transactiosSaving(String numAcc,String tipo, Double cash);
  
  Mono<Void> dltSaving(String id);

  Flux<SavingEntity> findBytitularesByDoc(SavingEntity saving);
  
  Flux<SavingEntity> findByDoc(String doc);
  
  Mono<EntityTransaction> payCreditCard(String numAcc,String numCard,Double cash);

  Mono<EntityTransaction> opeMovement(String numAcc,String numDest,Double cash,String type);
}