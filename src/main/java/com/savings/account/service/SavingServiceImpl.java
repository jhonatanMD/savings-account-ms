package com.savings.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.savings.account.model.SavingEntity;
import com.savings.account.repository.ISavingRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SavingServiceImpl implements ISavingService {

	@Autowired
	ISavingRepository repository;
	
	@Override
	public Flux<SavingEntity> allSaving() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Mono<SavingEntity> saveSaving(SavingEntity saving) {
		// TODO Auto-generated method stub
		return repository.save(saving);
	}

	@Override
	public Mono<SavingEntity> updSaving(SavingEntity saving) {
		// TODO Auto-generated method stub
		return repository.save(saving);
	}

	@Override
	public Mono<Void> dltSaving(String id) {
		// TODO Auto-generated method stub
		return repository.deleteById(id);
	}



}
