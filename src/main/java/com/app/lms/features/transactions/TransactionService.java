package com.app.lms.features.transactions;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.lms.features.books.Book;
import com.app.lms.features.books.BookRepository;
import com.app.lms.features.patrons.PatronRepository;
import com.app.lms.transaction.data.dto.Transaction.StatusEnum;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final BookRepository bookRepository;
	private final PatronRepository patronRepository;
	private final TransactionMapper transactionMapper;
	
	
	TransactionService(TransactionRepository transactionRepository, 
			BookRepository bookRepository, 
			PatronRepository patronRepository,
			TransactionMapper transactionMapper) {
		this.transactionRepository = transactionRepository;
		this.bookRepository = bookRepository;
		this.patronRepository = patronRepository;
		this.transactionMapper = transactionMapper;
		
	}
	
	public List<com.app.lms.transaction.data.dto.Transaction> getTransactions() {
		List<Transaction> transactionList = transactionRepository.findAll();
		List<com.app.lms.transaction.data.dto.Transaction> dtoTransactionList = transactionMapper.mapTransaction(transactionList);
		return dtoTransactionList;
		
	}
	
	public Optional<com.app.lms.transaction.data.dto.Transaction> getTransactionDetail(final Integer transactionId) {
		
		Optional<Transaction> newTransaction = transactionRepository.findById(transactionId);
		if (newTransaction.isPresent()) {
			return Optional.of(transactionMapper.mapTransaction(newTransaction.get()));
		} 
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Not Found"); 
	}
	
	@Transactional
	public com.app.lms.transaction.data.dto.Transaction addTransaction(final com.app.lms.transaction.data.dto.Transaction transaction) {
		
		validateBookAndPatron(transaction);
		Transaction newTransactionEntity = transactionMapper.mapTransaction(transaction);
		Book book = newTransactionEntity.getBook();
		if (book.getAvailableQuantity() > 0) {
			book.setAvailableQuantity(book.getAvailableQuantity() - 1);
			bookRepository.save(book);
			newTransactionEntity.setDuedate(LocalDate.now().plusDays(10));
			newTransactionEntity.setActionDate(LocalDate.now());
			newTransactionEntity.setIssueDate(LocalDate.now());
			newTransactionEntity.setReturnDate(null);
			newTransactionEntity.setFineAmount(null);
			Transaction newTransaction = transactionRepository.save(newTransactionEntity);
			return transactionMapper.mapTransaction(newTransaction);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book is not available for borrowing"); 
	}
	
	@Transactional
	public boolean updateTransaction(final com.app.lms.transaction.data.dto.Transaction transaction) {
		
		Optional<com.app.lms.transaction.data.dto.Transaction> transactionFromDB = getTransactionDetail(transaction.getTransactionId());
		validateBookAndPatron(transaction);
		if (StatusEnum.BORROWED.equals(transactionFromDB.get().getStatus()) 
				&& StatusEnum.RETURNED.equals(transaction.getStatus())) {
			Transaction newTransactionEntity = transactionMapper.mapTransaction(transaction);
			newTransactionEntity.setActionDate(LocalDate.now());
			newTransactionEntity.setDuedate(transactionFromDB.get().getDueDate());
			newTransactionEntity.setIssueDate(transactionFromDB.get().getIssueDate());
			newTransactionEntity.setStatus(StatusEnum.RETURNED.toString());
			newTransactionEntity.setReturnDate(LocalDate.now());
			newTransactionEntity.setFineAmount(calculateFineAmount(newTransactionEntity));
			Book book = newTransactionEntity.getBook();
			book.setAvailableQuantity(book.getAvailableQuantity() + 1);
			bookRepository.save(book);
			transactionRepository.save(newTransactionEntity);
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction does not exist in BORROWED state");
		}
	}
	
	private String calculateFineAmount(Transaction transactionEntity) {
		
		String fineAmount = "0";
		if (transactionEntity.getReturnDate().isAfter(transactionEntity.getDuedate())) { 
			long days = transactionEntity.getDuedate().until(transactionEntity.getReturnDate(), ChronoUnit.DAYS);
			System.out.println("days " + days );
			fineAmount = String.valueOf(days * 10);
		} 
		return fineAmount;
	}
	
	public void deleteTransaction(final Integer transactionId) {
		transactionRepository.deleteById(transactionId);
	}
	
	private void validateBookAndPatron(final com.app.lms.transaction.data.dto.Transaction transaction) {
		
		if (!bookExists((transaction.getBookId()))) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid book id");
		}
		
		if (!patronExists(transaction.getPatronId())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid patron id");
		}
	}

	private boolean bookExists(String bookId) {
		return bookRepository.existsById(Integer.parseInt(bookId));
	}
	
	private boolean patronExists(String patronId) {
		return patronRepository.existsById(Integer.parseInt(patronId));
	}
}
