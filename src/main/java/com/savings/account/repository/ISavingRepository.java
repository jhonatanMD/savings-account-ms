package com.savings.account.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.savings.account.model.SavingEntity;

@Repository
public interface ISavingRepository extends ReactiveMongoRepository<SavingEntity, String> {

	
}
