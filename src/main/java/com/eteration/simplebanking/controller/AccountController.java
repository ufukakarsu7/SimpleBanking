package com.eteration.simplebanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.BillPaymentTransaction;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.service.AccountService;
// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("/account/v1")
public class AccountController {
	private AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@RequestMapping(value = "createAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatus> createAccount(@RequestBody Account account){
    	TransactionStatus status = new TransactionStatus();
    	accountService.createAccount(account.getAccountNumber(),account.getOwner());
    	return new ResponseEntity<>(status.getStatus());
    }

	@RequestMapping(value = "getAccount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> getAccount(@RequestParam("accountNumber") String accountNumber) throws Exception {
        return new ResponseEntity<>(accountService.findAccount(accountNumber), HttpStatus.OK);
    }

    @RequestMapping(value = "credit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatus> credit(@RequestParam("accountNumber") String accountNumber, @RequestBody DepositTransaction transaction) throws InsufficientBalanceException {
    	TransactionStatus status = new TransactionStatus();
    	transaction.setApprovalCode(status.getApprovalCode());
    	accountService.credit(transaction, accountNumber);
        return new ResponseEntity<>(status, status.getStatus());
    }
    
    @RequestMapping(value = "debit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatus> debit(@RequestParam("accountNumber") String accountNumber, @RequestBody WithdrawalTransaction transaction) throws InsufficientBalanceException{
        TransactionStatus status = new TransactionStatus();
        transaction.setApprovalCode(status.getApprovalCode());
    	accountService.debit(transaction, accountNumber);
        return new ResponseEntity<>(status, status.getStatus());
	}
    
    @RequestMapping(value = "payee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatus> payee(@RequestParam("accountNumber") String accountNumber, @RequestBody BillPaymentTransaction transaction)  throws InsufficientBalanceException{
    	TransactionStatus status = new TransactionStatus();
    	transaction.setApprovalCode(status.getApprovalCode());
    	accountService.payee(transaction, transaction.getPayee(), accountNumber);
    	return new ResponseEntity<>(status, status.getStatus());
    }
    
    @RequestMapping(value = "getAllAccounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAllAccounts(){
    	return accountService.getAllAccounts();
    }
    
    
}