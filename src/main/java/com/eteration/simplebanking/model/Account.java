package com.eteration.simplebanking.model;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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
@NoArgsConstructor
@RequiredArgsConstructor
@Document("account")
public class Account {
	@Id
	@NonNull
	private String accountNumber;
	@NonNull
	private String owner;
	private double balance;
	private String createDate;
	private List<Transaction> transactions = new ArrayList<>();
	
	
	public void post(Transaction transaction) {
		transactions.add(transaction);
	}
	
	public void deposit(double amount) throws InsufficientBalanceException {
		setBalance(getBalance() + amount);
	}
	
	public void withdraw(double amount) throws InsufficientBalanceException {
		if (balance - amount < 0)
			throw new InsufficientBalanceException("Insufficient balance. Balance : " + getBalance());
		else {
			setBalance(getBalance() - amount);
		}
	}
}
