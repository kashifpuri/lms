package com.app.lms.features.transactions;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.lms.features.books.Book;
import com.app.lms.features.books.BookRepository;
import com.app.lms.features.patrons.Patron;
import com.app.lms.features.patrons.PatronRepository;
import com.app.lms.transaction.data.dto.Transaction.StatusEnum;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {
	
	@Autowired
	protected BookRepository bookRepository;
	
	@Autowired
	protected PatronRepository patronRepository;
	
	public Transaction mapTransaction(com.app.lms.transaction.data.dto.Transaction transaction) {
		
		Transaction transactionEntity = new Transaction();
		transactionEntity.setId(transaction.getTransactionId());
		transactionEntity.setActionDate(transaction.getActionDate());
		transactionEntity.setIssueDate(transaction.getIssueDate());
		transactionEntity.setReturnDate(transaction.getReturnDate());
		transactionEntity.setFineAmount(transaction.getFineAmount());
		transactionEntity.setComments(transaction.getComments());
		transactionEntity.setDuedate(transaction.getDueDate());
		transactionEntity.setStatus(transaction.getStatus().toString());
		
		Book book = bookRepository.getReferenceById(Integer.parseInt(transaction.getBookId()));
		transactionEntity.setBook(book);
		
		Patron patron = patronRepository.getReferenceById(Integer.parseInt(transaction.getPatronId()));
		transactionEntity.setPatron(patron);
		
		return transactionEntity;
		
	}
	
	public com.app.lms.transaction.data.dto.Transaction mapTransaction(Transaction transaction) {
		
		com.app.lms.transaction.data.dto.Transaction transactionDTO = new com.app.lms.transaction.data.dto.Transaction();
		transactionDTO.setTransactionId(transaction.getId());
		transactionDTO.setActionDate(transaction.getActionDate());
		transactionDTO.setComments(transaction.getComments());
		transactionDTO.setDueDate(transaction.getDuedate());
		transactionDTO.setStatus(StatusEnum.fromValue(transaction.getStatus()));
		
		transactionDTO.setBookId(transaction.getBook().getId()+"");
		
		transactionDTO.setPatronId(transaction.getPatron().getId()+"");
		
		transactionDTO.setIssueDate(transaction.getIssueDate());
		transactionDTO.setReturnDate(transaction.getReturnDate());
		transactionDTO.setFineAmount(transaction.getFineAmount());
		
		return transactionDTO;
	}

	public abstract List<com.app.lms.transaction.data.dto.Transaction> mapTransaction(List<Transaction> transactionList);
}
