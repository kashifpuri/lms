package com.app.lms.features.books;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @InjectMocks
    BookService bookService;

    @Test
    public void testGetAllBooks() {

        List<Book> books = new ArrayList<>();
        books.add(getBookEntity());

        List<com.app.lms.book.data.dto.Book> booksDto = new ArrayList<>();
        booksDto.add(getBookDto());

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.mapBook(anyList())).thenReturn(booksDto);

        Assertions.assertNotNull(bookService.getBooks());
        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).mapBook(anyList());
    }

    @Test
    public void testGetBookDetail() {

        Book bookEntity = getBookEntity();
        com.app.lms.book.data.dto.Book bookDto = getBookDto();

        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(bookEntity));
        when(bookMapper.mapBook(any(Book.class))).thenReturn(bookDto);

        Assertions.assertNotNull(bookService.getBookDetail(1));
        verify(bookRepository, times(1)).findById(anyInt());
        verify(bookMapper, times(1)).mapBook(any(Book.class));
    }
    @Test
    public void testGetEmptyBookDetail() {

        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
        	bookService.getBookDetail(1);
            });
        verify(bookRepository, times(1)).findById(anyInt());
        verify(bookMapper, times(0)).mapBook(any(Book.class));
    }

    @Test
    public void testAddBook() {

        Book bookEntity = getBookEntity();
        com.app.lms.book.data.dto.Book bookDto = getBookDto();

        when(bookMapper.mapBook(any(com.app.lms.book.data.dto.Book.class))).thenReturn(bookEntity);
        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);
        when(bookMapper.mapBook(any(Book.class))).thenReturn(bookDto);

        Assertions.assertNotNull(bookService.addBook(bookDto));
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).mapBook(any(Book.class));
        verify(bookMapper, times(1)).mapBook(any(com.app.lms.book.data.dto.Book.class));
    }

    @Test
    public void testUpdateBookTrue() {
        Book bookEntity = getBookEntity();
        com.app.lms.book.data.dto.Book bookDto = getBookDto();

        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(bookEntity));
        when(bookMapper.mapBook(any(Book.class))).thenReturn(bookDto);
        when(bookMapper.mapBook(any(com.app.lms.book.data.dto.Book.class))).thenReturn(bookEntity);
        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);

        Assertions.assertTrue(bookService.updateBook(bookDto));
        verify(bookRepository, times(1)).findById(anyInt());
        verify(bookMapper, times(1)).mapBook(any(Book.class));
        verify(bookMapper, times(1)).mapBook(any(com.app.lms.book.data.dto.Book.class));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBookFalse() {

        Book bookEntity = getBookEntity();
        com.app.lms.book.data.dto.Book bookDto = getBookDto();

        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> {
        	bookService.updateBook(bookDto);
            });
        verify(bookRepository, times(1)).findById(anyInt());
        verify(bookMapper, times(0)).mapBook(any(Book.class));
        verify(bookMapper, times(0)).mapBook(any(com.app.lms.book.data.dto.Book.class));
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    public void testDeleteBook() {

        doNothing().when(bookRepository).deleteById(anyInt());
        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(anyInt());
    }

    private Book getBookEntity() {
        Book bookEntity = new Book();
        bookEntity.setId(4);
        bookEntity.setAuthor("Author");
        bookEntity.setIsbn("7612312312321");
        bookEntity.setGenre("Fiction");
        bookEntity.setTotalQuantity(10);
        bookEntity.setTitle("My Title");
        bookEntity.setRating("8");
        return bookEntity;
    }

    private com.app.lms.book.data.dto.Book getBookDto() {
        com.app.lms.book.data.dto.Book bookDto = new com.app.lms.book.data.dto.Book();
        bookDto.setBookId(4);
        bookDto.setAuthor("Author");
        bookDto.setIsbn("7612312312321");
        bookDto.setGenre("Fiction");
        bookDto.setTotalQuantity(10);
        bookDto.setTitle("My Title");
        bookDto.setRating("8");
        return bookDto;
    }
}

