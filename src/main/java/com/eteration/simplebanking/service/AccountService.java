package com.eteration.simplebanking.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.BillPaymentTransaction;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;

@Service
public class AccountService{
	
	private AccountRepository accountRepository;
	
	@Autowired
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public void createAccount(String accountNumber,String owner) {
		Account newAccount = new Account();
		newAccount.setAccountNumber(accountNumber);
		newAccount.setOwner(owner);
		newAccount.setCreateDate(getCurrentTime());
		accountRepository.save(newAccount);
	}
	
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	public void credit(Transaction transaction, String accountNumber) throws InsufficientBalanceException{
		Account account =  findAccount(accountNumber);
		account.deposit(transaction.getAmount());
		DepositTransaction depositTransaction = new DepositTransaction(transaction.getAmount());
		depositTransaction.setApprovalCode(transaction.getApprovalCode());
		depositTransaction.setDate(getCurrentTime());
		depositTransaction.setType(transaction.getClass().getSimpleName());
		account.post(depositTransaction);
		accountRepository.save(account);
	}

	
	public void debit(Transaction transaction, String accountNumber)  throws InsufficientBalanceException{
		Account account =  findAccount(accountNumber);
		account.withdraw(transaction.getAmount());
		WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(transaction.getAmount());
		withdrawalTransaction.setApprovalCode(transaction.getApprovalCode());
		withdrawalTransaction.setDate(getCurrentTime());
		withdrawalTransaction.setType(transaction.getClass().getSimpleName());
		account.post(withdrawalTransaction);
		accountRepository.save(account);
	}
	
	public void payee(Transaction transaction, String payee,String accountNumber) throws InsufficientBalanceException{
		Account account =  findAccount(accountNumber);
		account.withdraw(transaction.getAmount());
		BillPaymentTransaction billPaymentTransaction = new BillPaymentTransaction(transaction.getAmount(),payee);
		billPaymentTransaction.setApprovalCode(transaction.getApprovalCode());
		billPaymentTransaction.setDate(getCurrentTime());
		billPaymentTransaction.setType(transaction.getClass().getSimpleName());
		billPaymentTransaction.setPayee(payee);
		account.post(billPaymentTransaction);
		accountRepository.save(account);
		
	}
	
	public Account findAccount(String accountNumber) {
		Optional<Account> account = accountRepository.findById(accountNumber);
		if (!account.isPresent())
			throw new NullPointerException();
		return account.get();
	}
	
	
	
	private String getCurrentTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String formattedCurrentDate = ZonedDateTime.now().format(formatter);
		
		return formattedCurrentDate;
	}

	
	

}
