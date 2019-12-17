package com.savings.account.model;

import reactor.core.publisher.Mono;

public class ResponseService {

	private String msj;
	private Mono<SavingEntity> savingEntity;
	
	public String getMsj() {
		return msj;
	}
	public void setMsj(String msj) {
		this.msj = msj;
	}
	public Mono<SavingEntity> getSavingEntity() {
		return savingEntity;
	}
	public void setSavingEntity(Mono<SavingEntity> savingEntity) {
		this.savingEntity = savingEntity;
	}
	
	
	
}
