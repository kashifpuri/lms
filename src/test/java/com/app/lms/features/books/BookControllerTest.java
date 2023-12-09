package com.app.lms.features.books;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.lms.JsonUtils;
import com.app.lms.book.data.dto.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test void testGetBooksUnAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testGetBooks() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.books").exists())
                .andExpect(jsonPath("$.books.size()").value(3))
                .andExpect(jsonPath("$.books[*].bookId").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetBookDetails() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.isbn").value("ISBN-10 0-596-52068-9"))
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("Sapiens - A Brief History of Humankind"))
                .andExpect(jsonPath("$.genre").value("Non-Fiction"))
                .andExpect(jsonPath("$.rating").isNotEmpty())
                .andExpect(jsonPath("$.author").value("Yuval Noah Harari"))
                .andExpect(jsonPath("$.totalQuantity").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addBook() throws Exception{

        Book newBook = new Book();
        newBook.setBookId(4);
        newBook.setAuthor("Author");
        newBook.setIsbn("ISBN-10: 0-596-52068-9");
        newBook.setGenre("Fiction");
        newBook.setTotalQuantity(10);
        newBook.setAvailableQuantity(10);
        newBook.setTitle("My Title");
        newBook.setRating("8");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtils.asJsonString(newBook))
                .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.book.isbn").value("ISBN-10: 0-596-52068-9"))
                .andExpect(jsonPath("$.book.bookId").value(4))
                .andExpect(jsonPath("$.book.title").value("My Title"))
                .andExpect(jsonPath("$.book.genre").value("Fiction"))
                .andExpect(jsonPath("$.book.rating").value("8"))
                .andExpect(jsonPath("$.book.author").value("Author"))
                .andExpect(jsonPath("$.book.totalQuantity").value(10))
                .andExpect(jsonPath("$.book.availableQuantity").value(10))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateBook() throws Exception{

        Book updateBook = new Book();
        updateBook.setBookId(2);
        updateBook.setTitle("Sapiens - A Brief History of Humankind");
        updateBook.setAuthor("Yuval Noah Harari");
        updateBook.setIsbn("0-596-52068-9");
        updateBook.setGenre("Non-Fiction");
        updateBook.setTotalQuantity(50);
        updateBook.setAvailableQuantity(50);
        updateBook.setRating("8");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/book/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.asJsonString(updateBook))
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.isbn").value("0-596-52068-9"))
                .andExpect(jsonPath("$.bookId").value(2))
                .andExpect(jsonPath("$.title").value("Sapiens - A Brief History of Humankind"))
                .andExpect(jsonPath("$.genre").value("Non-Fiction"))
                .andExpect(jsonPath("$.rating").value("8"))
                .andExpect(jsonPath("$.author").value("Yuval Noah Harari"))
                .andExpect(jsonPath("$.totalQuantity").value(50))
                .andExpect(jsonPath("$.availableQuantity").value(50))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateNotAvailableBook() throws Exception{

        Book updateBook = new Book();
        updateBook.setBookId(10);
        updateBook.setTitle("Sapiens - A Brief History of Humankind");
        updateBook.setAuthor("Yuval Noah Harari");
        updateBook.setIsbn("0-596-52068-9");
        updateBook.setGenre("Non-Fiction");
        updateBook.setTotalQuantity(50);
        updateBook.setAvailableQuantity(50);
        updateBook.setRating("8");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/book/10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.asJsonString(updateBook))
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/book/3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/book/6")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isNotFound());
    }
}
