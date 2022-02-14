package com.eteration.simplebanking.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// This class is a place holder you can change the complete implementation
@Data
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public abstract class Transaction {
	private String date;
	@NonNull
	private Double amount;
	private String approvalCode;
	private String type;
	
	
	
	
}
