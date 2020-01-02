package com.savings.account.webclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.savings.account.model.EntityDebtor;
import com.savings.account.model.EntityTransaction;
import com.savings.account.model.SavingEntity;
import com.savings.account.repository.ISavingRepository;

import reactor.core.publisher.Mono;

@Component
@Qualifier("webClient")
public class CallWebClient {

	 WebClient client = WebClient.builder().baseUrl("http://localhost:8881")
			  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	 
	 @Autowired
		ISavingRepository repository;
	 
	 public Mono<EntityTransaction> payCredit(EntityTransaction transaction,SavingEntity p  ,
			 String numAcc,String numDest,Double cash,Double commi,int n){
		return client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numDest+"/p/"+cash)
				.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
					
			 		transaction.setCashA(p.getCash());
					p.setNumTran(p.getNumTran() - n);
					p.setCash(p.getCash() - cash - commi);
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setNumAcc(numAcc);
					transaction.setCommi(commi);
					transaction.setType("r");
					transaction.setDateTra(tran.getDateTra());
					
					repository.save(p).subscribe();
			 return Mono.just(transaction);
			
		});	
		 
	 }
	 
	 public Mono<EntityTransaction> opeCurrent(EntityTransaction transaction,SavingEntity p  ,String numAcc ,String numDest,Double cash
			 ,Double commi,int n){
		  
			return  client.post().uri("/current-account/api/updTransancionesCurrent/"+numDest+"/d/"+cash)
				.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
					
			 		transaction.setCashA(p.getCash());
			 		p.setNumTran(p.getNumTran() - n);
					p.setCash(p.getCash() - cash - commi);
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setNumAcc(numAcc);
					transaction.setCommi(commi);
					transaction.setType("r");
					transaction.setDateTra(tran.getDateTra());
					repository.save(p).subscribe();
					
			 return Mono.just(transaction);
			
				});		
		  }
	 
	 
	 
	 public Mono<EntityDebtor> responde (List<String> numDoc){
		 return client.post().uri("/bank/api/getDeudas").syncBody(numDoc)
			.retrieve().bodyToMono(EntityDebtor.class).flatMap(rs -> {	
					return Mono.just(rs);
			});
	 }
	
}
