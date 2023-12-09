package com.app.lms.features.books;

import static com.app.lms.features.books.Constants.ADD_BOOK;
import static com.app.lms.features.books.Constants.DELETE_BOOK;
import static com.app.lms.features.books.Constants.GET_BOOKS;
import static com.app.lms.features.books.Constants.GET_BOOK_DETAIL;
import static com.app.lms.features.books.Constants.UPDATE_BOOK;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.app.lms.book.data.dto.AddBookResponse;
import com.app.lms.book.data.dto.BooksListResponse;

@RestController
@Validated
public class BookController {

	private final BookService bookService;
	
	BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping(value = GET_BOOKS, produces = MediaType.APPLICATION_JSON_VALUE)
	public BooksListResponse getBooksList() {
		BooksListResponse response = new BooksListResponse();
		response.setBooks(bookService.getBooks());
		return response;
	}
	
	@GetMapping(value = GET_BOOK_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public com.app.lms.book.data.dto.Book getBookDetail(
			@PathVariable(value = "bookId", required = true) @Valid @NotNull Integer bookId) {
		Optional<com.app.lms.book.data.dto.Book> book = 
				bookService.getBookDetail(bookId);
		return book.get();
	}
	
	@PostMapping(value = ADD_BOOK, produces = MediaType.APPLICATION_JSON_VALUE)
	public AddBookResponse addBook(
			@Valid @RequestBody com.app.lms.book.data.dto.Book book) {
		AddBookResponse response = new AddBookResponse();
		response.setBook(bookService.addBook(book));
		return response;
	}
	
	@PutMapping(value = UPDATE_BOOK, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateBook(
			@Valid @RequestBody com.app.lms.book.data.dto.Book book) {
		bookService.updateBook(book);
	}
	
	@DeleteMapping(value = DELETE_BOOK, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteBook(@PathVariable(value = "bookId", required = true) @Valid @NotNull Integer bookId) {
		bookService.deleteBook(bookId);
	}
	
	
}
