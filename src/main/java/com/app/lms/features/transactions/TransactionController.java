package com.app.lms.features.transactions;

import static com.app.lms.features.transactions.Constants.ADD_TRANSACTION;
import static com.app.lms.features.transactions.Constants.DELETE_TRANSACTION;
import static com.app.lms.features.transactions.Constants.GET_TRANSACTIONS;
import static com.app.lms.features.transactions.Constants.GET_TRANSACTION_DETAIL;
import static com.app.lms.features.transactions.Constants.UPDATE_TRANSACTION;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.lms.transaction.data.dto.AddTransactionResponse;
import com.app.lms.transaction.data.dto.TransactionsListResponse;

@RestController
public class TransactionController {

	private final TransactionService transactionService;
	
	TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@GetMapping(value = GET_TRANSACTIONS, produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionsListResponse getTransactionsList() {
		TransactionsListResponse response = new TransactionsListResponse();
		response.setTransactions(transactionService.getTransactions());
		return response;
	}
	
	@GetMapping(value = GET_TRANSACTION_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public com.app.lms.transaction.data.dto.Transaction getTransactionDetail(
			@PathVariable(value = "transactionId", required = true) @Valid @NotNull Integer transactionId) {
		Optional<com.app.lms.transaction.data.dto.Transaction> transaction = 
				transactionService.getTransactionDetail(transactionId);
		return transaction.get();
	}
	
	@PostMapping(value = ADD_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
	public AddTransactionResponse addTransaction(
			@Valid @RequestBody com.app.lms.transaction.data.dto.Transaction transaction) {
		AddTransactionResponse response = new AddTransactionResponse();
		response.setTransaction(transactionService.addTransaction(transaction));
		return response;
	}
	
	@PutMapping(value = UPDATE_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTransaction(
			@Valid @RequestBody com.app.lms.transaction.data.dto.Transaction transaction) {
		transactionService.updateTransaction(transaction);
	}
	
	@DeleteMapping(value = DELETE_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTransaction(@PathVariable(value = "transactionId", required = true) @Valid @NotNull Integer transactionId) {
		transactionService.deleteTransaction(transactionId);
	}
	
	
}
