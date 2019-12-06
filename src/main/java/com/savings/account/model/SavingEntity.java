package com.savings.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import reactor.core.publisher.Flux;

@Document(collection = "SavingAccount")
public class SavingEntity {
	
	@Id
	private String codSav;
	
	private String numAcc;
	
	private Double cash;
	
	private String codStaf;
	
	private Flux<HeadLineEntity> head;

	public String getCodSav() {
		return codSav;
	}

	public void setCodSav(String codSav) {
		this.codSav = codSav;
	}

	public String getNumAcc() {
		return numAcc;
	}

	public void setNumAcc(String numAcc) {
		this.numAcc = numAcc;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	public String getCodStaf() {
		return codStaf;
	}

	public void setCodStaf(String codStaf) {
		this.codStaf = codStaf;
	}

	public Flux<HeadLineEntity> getHead() {
		return head;
	}

	public void setHead(Flux<HeadLineEntity> head) {
		this.head = head;
	}
	
	//private Flux<String> firm;



/*	public Flux<String> getFirm() {
		return firm;
	}

	public void setFirm(Flux<String> firm) {
		this.firm = firm;
	}
	*/
}
