package com.savings.account.repository;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Repository;

import com.savings.account.model.SavingEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface ISavingRepository extends ReactiveMongoRepository<SavingEntity, String> {
        Mono<SavingEntity> findByNumAcc (String numAcc);
	@Query("{ 'heads.dniH': {$in:[ ?0 ]} , 'profile':?1 ,'bank':?2 }")
	Flux<SavingEntity> findBytitularesByDocProfileByBank(List<String> doc ,String profile,String bank);
	
	@Query("{ 'heads.dniH': {$in:[ ?0 ]} }")
	Flux<SavingEntity> findBytitularesByDoc(List<String> doc);

	
	@Query("{'heads.dniH':  ?0}")
	Flux<SavingEntity> findByDoc(String doc);

}
