package com.savings.account.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EntityDebtor")
public class EntityDebtor {

	@Id
	private String id;
	
	private List<String> numDoc;
	
	private String status;
	
	private String msj;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public List<String> getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(List<String> numDoc) {
		this.numDoc = numDoc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsj() {
		return msj;
	}

	public void setMsj(String msj) {
		this.msj = msj;
	}
	
	
	
	
}
