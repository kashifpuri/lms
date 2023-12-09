package com.app.lms.features.books;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookService {
	
	private final BookRepository bookRepository;
	private final BookMapper bookMapper;
	
	
	BookService(BookRepository bookRepository, 
			BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.bookMapper = bookMapper;
		
	}
	
	public List<com.app.lms.book.data.dto.Book> getBooks() {
		List<Book> bookList = bookRepository.findAll();
		List<com.app.lms.book.data.dto.Book> dtoBookList = bookMapper.mapBook(bookList);
		return dtoBookList;
		
	}
	
	public Optional<com.app.lms.book.data.dto.Book> getBookDetail(final Integer bookId) {
		
		Optional<Book> newBook = bookRepository.findById(bookId);
		if (newBook.isPresent()) {
			return Optional.of(bookMapper.mapBook(newBook.get()));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
	}
	
	public com.app.lms.book.data.dto.Book addBook(final com.app.lms.book.data.dto.Book book) {
		Book newBookEntity = bookMapper.mapBook(book);
		Book newBook = bookRepository.save(newBookEntity);
		return bookMapper.mapBook(newBook);
		
	}
	
	public boolean updateBook(final com.app.lms.book.data.dto.Book book) {
		
		if(getBookDetail(book.getBookId()).isPresent()) {
			Book newBookEntity = bookMapper.mapBook(book);
			bookRepository.save(newBookEntity);
			return true;
		} 
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
	}
	
	public void deleteBook(final Integer bookId) {
		bookRepository.deleteById(bookId);
	}

}
