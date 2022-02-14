package com.eteration.simplebanking.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

// This class is a place holder you can change the complete implementation

@Getter
public class TransactionStatus{
	private HttpStatus status = HttpStatus.OK;
	private String approvalCode = generateUUId();
	
	private String generateUUId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
