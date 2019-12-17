package com.savings.account.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.savings.account.model.SavingEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ISavingRepository extends ReactiveMongoRepository<SavingEntity, String> {
	
	Mono<SavingEntity> findByNumAcc(String numAcc);
	
	@Query("{ 'heads.dniH': {$in:[ ?0 ]} , 'profile':?1 }")
	Flux<SavingEntity> findBytitularesByDocProfile(List<String> doc ,String profile);
	
	@Query("{ 'heads.dniH': {$in:[ ?0 ]} }")
	Flux<SavingEntity> findBytitularesByDoc(List<String> doc);

	
	@Query("{'heads.dniH':  ?0}")
	Mono<SavingEntity> findByDoc(String doc);
}
