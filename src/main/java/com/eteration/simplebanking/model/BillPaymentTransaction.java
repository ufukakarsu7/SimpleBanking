package com.eteration.simplebanking.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BillPaymentTransaction extends Transaction{
	@NonNull
	private String payee;
	
	public BillPaymentTransaction() {
		super();
	}
	
	public BillPaymentTransaction(double amount,String payee) {
		super(amount);
		this.payee = payee;
	}
	
	
}
