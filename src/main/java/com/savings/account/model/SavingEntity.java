package com.savings.account.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "SavingAccount")
public class SavingEntity {
	
	@Id
	private String codSav;
	
	private String numAcc;
	
	private Double cash;
	
	private String profile;
	
	private Double cashEndMonth;
	
	private int numTran;
	
	private Double commi;
	
	private String status;
	private String bank;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@NotNull
	private Date dateOpen;
  private List<HeadLineEntity> heads;
	
	private List<SignatoriesEntity> sigs;
	
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

	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}


	public Double getCashEndMonth() {
		return cashEndMonth;
	}

	public void setCashEndMonth(Double cashEndMonth) {
		this.cashEndMonth = cashEndMonth;
	}

	public int getNumTran() {
		return numTran;
	}

	public void setNumTran(int numTran) {
		this.numTran = numTran;
	}

	
	public Double getCommi() {
		return commi;
	}

	public void setCommi(Double commi) {
		this.commi = commi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public Date getDateOpen() {
		return dateOpen;
	}

	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}

	public List<HeadLineEntity> getHeads() {
		return heads;
	}

	public void setHeads(List<HeadLineEntity> heads) {
		this.heads = heads;
	}

	public List<SignatoriesEntity> getSigs() {
		return sigs;
	}

	public void setSigs(List<SignatoriesEntity> sigs) {
		this.sigs = sigs;
	}

}
