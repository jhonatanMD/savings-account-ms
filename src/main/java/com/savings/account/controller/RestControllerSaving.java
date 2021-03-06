package com.savings.account.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.savings.account.model.EntityTransaction;
import com.savings.account.model.SavingEntity;
import com.savings.account.service.ISavingService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class RestControllerSaving {

  @Autowired
  ISavingService savingImpl;

	
	@GetMapping("/getSavings")
	Flux<SavingEntity> getSavings(){	
		//getDTO().subscribe();
		return savingImpl.allSaving();
	}
	
	@GetMapping("/getSaving/{numAcc}")
	Mono<SavingEntity> getSavingNumAcc(@PathVariable("numAcc") String numAcc){	
		
		return savingImpl.findByNumAcc(numAcc);
	}
	
	@GetMapping("/getSavingDocu/{numDoc}")
	Flux<SavingEntity> getSavingDoc(@PathVariable("numDoc") String numDoc){	
		return savingImpl.findByDoc(numDoc);
	}
	
	@GetMapping("/getSavingDoc")
	Flux<SavingEntity> getSavingDoc(@RequestBody SavingEntity savingEntity){	
		return savingImpl.findBytitularesByDoc(savingEntity);
	}
	
	@GetMapping("/getSavingDates/{dt1}/{dt2}/{bank}")
	Flux<SavingEntity> getSaving(@PathVariable("dt1") String dt1
			,@PathVariable("dt2") String dt2,@PathVariable("bank") String bank) throws ParseException{	
		return savingImpl.findByAccount(dt1, dt2, bank);
	}
	
	
	@PostMapping("/postSaving")
	Mono<SavingEntity> postSaving(@RequestBody SavingEntity savingEntity){
		return savingImpl.saveSaving(savingEntity);
		
		
		
/*
			return savingImpl.findByDni(savingEntity.getDniCli())
					.switchIfEmpty(savingImpl.saveSaving(savingEntity).flatMap(sv->{
						
						return Mono.just(sv);
					}));*/
		
			/*		return savingImpl.saveSaving(savingEntity).flatMap( c ->{
						postCustomer(c.getCustomer()).subscribe();
						postS(savingEntity.getHeads(),c.getCodSav()).subscribe();	
						 return Mono.just(c);
					});*/
	}


	@PostMapping("/updTransancionSaving/{numAcc}/{tipo}/{cash}")
	Mono<EntityTransaction> updTransancionSaving(@PathVariable("numAcc") String numAcc
			,@PathVariable("tipo") String tipo ,@PathVariable("cash")  Double cash){
	
			return savingImpl.transactiosSaving(numAcc,tipo,cash);

	}
	
	
	/*@PostMapping("/payCreditCard/{numAcc}/{numCard}/{cash}")
	Mono<EntityTransaction> payCreditCard(@PathVariable("numAcc") String numAcc,
			@PathVariable("numCard") String numCard,
			@PathVariable("cash")  Double cash){
	
			return savingImpl.payCreditCard(numAcc,numCard,cash);

	}
	*/
	
	@PostMapping("/opeMovementSaving/{numAcc}/{numDest}/{cash}/{type}")
	Mono<EntityTransaction> opeMovement(@PathVariable("numAcc") String numAcc,
			@PathVariable("numDest") String numDest,
			@PathVariable("cash")  Double cash,@PathVariable("type") String type){
	
			return savingImpl.opeMovement(numAcc,numDest,cash,type);

	}
	
	
	
	@PutMapping("/updSaving/{id}")
	Mono<SavingEntity> updSaving(@PathVariable("id") String id,@RequestBody SavingEntity saving){
		saving.setCodSav(id);
		return savingImpl.updSaving(saving);
	}
	
	/*	client.get().uri("/getSavings")
		.retrieve().bodyToMono(SavingEntity.class)
		
	*/
	
	//pendiente a verifiacar
	
	
/*	@PostMapping("/postSaving/{id}")
	Mono<EntityDTO> postSaving(@RequestBody SavingEntity saving,@PathVariable String id){
		saving.setCodStaf(id);
		return savingImpl.saveSaving(saving);
	}
	


	
	@PostMapping("/postS")
	Mono<String> postSaving1(@RequestBody Enti n){
		
		return client.post().uri("/postP").syncBody(n.getHead()).retrieve().bodyToMono(String.class);//savingImpl.saveSaving(n);
	}
	*/

	//@GetMapping("/getDTO")
	
	

	
	
}
