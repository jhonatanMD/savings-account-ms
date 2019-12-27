package com.savings.account.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.savings.account.model.EntityTransaction;
import com.savings.account.model.SavingEntity;
import com.savings.account.repository.ISavingRepository;
import com.savings.account.webclient.CallWebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SavingServiceImpl implements ISavingService {

  @Autowired
  ISavingRepository repository;
  
  @Autowired
  @Qualifier("webClient")
  CallWebClient webClient;
  
  EntityTransaction transaction;
  List<EntityTransaction> listTransaction;
  Date dt = new Date();
  List<String> doc;
  Boolean ope;
  Double commi;
  int num;
  String msg;
  @Override
  public Flux<SavingEntity> allSaving() {
    return repository.findAll();
  }


  @Override
  public Mono<Void> dltSaving(String id) {
    return repository.deleteById(id);
  }

  @Override
  public Mono<SavingEntity> saveSaving(SavingEntity saving) {

    List<String> doc = new ArrayList<>();
    
    if(saving.getProfile().equals("N")) {
    	saving.setCashEndMonth(0.0);
    }
    saving.setDateOpen(new Date());
    saving.getHeads().forEach(head -> doc.add(head.getDniH()));
    
   return repository.findBytitularesByDocProfileByBank(doc,saving.getProfile(),saving.getBank())
	.switchIfEmpty(
		  webClient.responde(doc).flatMap(s ->{
			if(s.getMsg().equals("")) {
			return repository.save(saving).flatMap(sv -> {
				  return Mono.just(sv);
				});
		    }else {
		    	return Mono.just(null);
		    }
			
		  })
		).next();
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
	public Mono<EntityTransaction> transactiosSaving(String numAcc,String tipo, Double cash) {
		return repository.findByNumAcc(numAcc)
				.flatMap(p ->{
					ope = false;
				//	Collections.sort(p.getTransactions(),Collections.reverseOrder());
					transaction = new EntityTransaction();
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
									transaction.setCommi(p.getCommi());
								}
							}
							
						}
					
				
				if(ope) {
					transaction.setType(tipo);
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setDateTra(new Date());
					transaction.setNumAcc(numAcc);
					repository.save(p).subscribe();
					return Mono.just(transaction);// ;
				}else {
					
					return Mono.just(transaction);
				}
					
			});
	}


	@Override
	public Flux<SavingEntity> findByDoc(String doc) {
		// TODO Auto-generated method stub
		return repository.findByDoc(doc);
	}


	@Override
	public Mono<EntityTransaction> payCreditCard(String numAcc, String numCard, Double cash) {
		// TODO Auto-generated method stub
		transaction = new EntityTransaction() ;
		
		return repository.findByNumAcc(numAcc).flatMap(p ->{
		/*	
			if(p.getCash() >= cash && p.getNumTran() > 0) {
			return	client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numCard+"/p/"+cash)
				.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
		
					 		transaction.setCashA(p.getCash());
							p.setNumTran(p.getNumTran() -1);
							p.setCash(p.getCash() - cash);
							transaction.setNumAcc(numAcc);
							transaction.setCashO(cash);
							transaction.setCashT(p.getCash());
							transaction.setCommi(0.0);
							transaction.setType("r");
							transaction.setDateTra(tran.getDateTra());
							repository.save(p).subscribe();
					 return Mono.just(transaction);
					
				});	
			}else if(p.getCash() >= cash + p.getCommi() && p.getNumTran() == 0){
				return	client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numCard+"/p/"+cash)
						.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
				
							 		transaction.setCashA(p.getCash());
									p.setCash(p.getCash() - cash - p.getCommi());
									transaction.setCashO(cash);
									transaction.setNumAcc(numAcc);
									transaction.setCashT(p.getCash());
									transaction.setCommi(p.getCommi());
									transaction.setType("r");
									transaction.setDateTra(tran.getDateTra());
									repository.save(p).subscribe();
									
							 return Mono.just(transaction);
							
						});	

			}*/
			
			
			return Mono.just(transaction);
			
		});
		 
		// return Mono.just(transaction);
	}


	@Override
	public Mono<EntityTransaction> opeMovement(String numAcc, String numDest, Double cash, String type) {
		// TODO Auto-generated method stub
		ope = false;
		transaction = new EntityTransaction();
		return repository.findByNumAcc(numAcc).flatMap(p ->{
			
			if(p.getCash() >= cash && p.getNumTran() > 0) {
				 commi = 0.0;
				 num = 1;
				 ope = true;
			}else if(p.getCash() >= cash + p.getCommi() && p.getNumTran() == 0){
				commi = p.getCommi();
				num = 0;
				ope = true;
			}

			if(ope) {
				if(type.equals("CC")) {
					return	webClient.payCredit(transaction, p, numAcc, numDest, cash,commi,num);
				}else if(type.equals("CA")) {
					return webClient.opeCurrent(transaction, p, numAcc, numDest, cash,commi,num);
				}else if(type.equals("SA")) {
					repository.findByNumAcc(numDest).flatMap(savingDest ->{
						
						transaction.setCashA(p.getCash());
						p.setNumTran(p.getNumTran() - num);
						p.setCash(p.getCash() - cash - commi);
						transaction.setCashO(cash);
						transaction.setCashT(p.getCash());
						transaction.setNumAcc(numAcc);
						transaction.setCommi(commi);
						transaction.setType("r");
						transaction.setDateTra(new Date());
						
						savingDest.setCash(savingDest.getCash() + cash);
						
						repository.save(savingDest).subscribe();
						repository.save(p).subscribe();
						
						return Mono.just(savingDest);
					}).subscribe();
				}
			}
			
			return Mono.just(transaction);
			
		});
	}


	@Override
	public Flux<SavingEntity> findByAccount(String doc, String dt1, String dt2, String bank) throws ParseException {
		

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return repository.findByBankAndDateOpenBetween(bank,format.parse(dt1),format.parse(dt2))
				.flatMap(res -> {
					return Flux.fromIterable(res.getHeads()).flatMap( sv -> {
							if(sv.getDniH().equals(doc)) {
								return Flux.just(res);
							}
							return Flux.empty();
					});
				});
	}
	
	


/*	public Mono<String> consume(EntityDTO dto) {
		// TODO Auto-generated method stub
		return  client.post().uri("/postP").syncBody(dto.getHead()).retrieve().bodyToMono(String.class);

	}
*/

}
