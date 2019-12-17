package com.savings.account.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.savings.account.model.EntityCreditCard;
import com.savings.account.model.EntityTransaction;
import com.savings.account.model.SavingEntity;
import com.savings.account.repository.ISavingRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SavingServiceImpl implements ISavingService {

	WebClient client = WebClient.builder().baseUrl("http://localhost:8881")
	.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

	@Autowired
	ISavingRepository repository;
	
	EntityTransaction transaction;
	List<EntityTransaction> listTransaction;
	Date dt = new Date();
	List<String> doc;
	Boolean ope = false;
	
	@Override
	public Flux<SavingEntity> allSaving() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}


	@Override
	public Mono<Void> dltSaving(String id) {
		// TODO Auto-generated method stub	
		return repository.deleteById(id);
	}

	@Override
	public Mono<SavingEntity> saveSaving(SavingEntity saving) {

		List<String> doc = new ArrayList<>();
		saving.getHeads().forEach(head -> doc.add(head.getDniH()));
		return repository.findBytitularesByDocProfile(doc,saving.getProfile())
				.switchIfEmpty(repository.save(saving).flatMap(sv->{
			return Mono.just(sv);
		})).next();
		
	}


	@Override
	public Mono<SavingEntity> updSaving(SavingEntity saving) {
		// TODO Auto-generated method stub
		return repository.save(saving);
	}


	@Override
	public Mono<SavingEntity> findByNumAcc(String numAcc) {
		// TODO Auto-generated method stub
		return repository.findByNumAcc(numAcc);
	}


	@Override
	public Flux<SavingEntity> findBytitularesByDoc(SavingEntity saving) {
		
		doc = new ArrayList<>();
		saving.getHeads().forEach(head-> doc.add(head.getDniH())); 	
		return repository.findBytitularesByDoc(doc);
	}


	//Registrar Transacciones de la cuenta de ahorro
	@Override
	public Mono<SavingEntity> transactiosSaving(String numAcc,String tipo, Double cash) {
		return repository.findByNumAcc(numAcc)
				.flatMap(p ->{
				//	Collections.sort(p.getTransactions(),Collections.reverseOrder());
					transaction = new EntityTransaction();
					listTransaction = new ArrayList<>();
					transaction.setCashA(p.getCash());
									
						if(p.getNumTran() > 0) {
							p.setNumTran(p.getNumTran() -1);
							transaction.setCommi(0.0);
							if(tipo.equals("r") && p.getCash() >= cash) {
								ope = true;
								p.setCash(p.getCash() - cash);
							}else if (tipo.equals("d")){
								ope = true;
								p.setCash( p.getCash() + cash);
							}
						}else {
							
							if(tipo.equals("r") && p.getCash() >= cash + p.getCommi()) {
								ope = true;
								p.setCash(p.getCash() - cash - p.getCommi());
								transaction.setCommi(p.getCommi());
							}else if (tipo.equals("d")){
								if(p.getCash() != 0.0) {
									ope = true;
									p.setCash( p.getCash() + cash - p.getCommi());
								}
							}
							
						}
					
				
				if(ope) {
					transaction.setType(tipo);
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setDateTra(dt);
					
					if(p.getTransactions() != null) {
					p.getTransactions().forEach(transac-> {
						listTransaction.add(transac);
						});
					}
					listTransaction.add(transaction);
					p.setTransactions(listTransaction);
					return repository.save(p);
				}else {
					
					return Mono.just(p);
				}
					
			});
	}


	@Override
	public Mono<SavingEntity> findByDoc(String doc) {
		// TODO Auto-generated method stub
		return repository.findByDoc(doc);
	}


	@Override
	public Mono<SavingEntity> payCreditCard(String numAcc, String numCard, Double cash) {
		// TODO Auto-generated method stub
		transaction = new EntityTransaction() ;
		listTransaction = new ArrayList<>();
		
		return repository.findByNumAcc(numAcc).flatMap(p ->{
			
			transaction.setCashA(p.getCash());
			if(p.getCash() >= cash) {
				p.setCash(p.getCash() - cash);
				System.out.println(numAcc +" cash : "+cash);
				
				client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numCard+"/p/"+cash)
				.retrieve().bodyToMono(EntityCreditCard.class).subscribe();	
			}
			transaction.setType("pago");
			 transaction.setCashO(cash);
			 transaction.setCashT(p.getCash());
			 transaction.setDateTra(dt);
			listTransaction = new ArrayList<>();
			if(p.getTransactions()!=null)
			{
				p.getTransactions().forEach(transac-> {
					listTransaction.add(transac);
				});
			}
			listTransaction.add(transaction);
			p.setTransactions(listTransaction);
	return repository.save(p);
		});
	}
	
	


/*	public Mono<String> consume(EntityDTO dto) {
		// TODO Auto-generated method stub
		return  client.post().uri("/postP").syncBody(dto.getHead()).retrieve().bodyToMono(String.class);

	}
*/

}
