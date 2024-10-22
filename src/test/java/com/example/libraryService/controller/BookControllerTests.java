package com.example.libraryService.controller;

import com.example.libraryService.config.TestConfig;
import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;

    private BookDTO bookDTO;

    @BeforeEach
    public void setUp() {
        book = new Book(
                1L,
                "New Book",
                "Genre",
                "Raepiobreaenbenbeabn",
                "Author",
                "3960296492591"
        );

        bookDTO = new BookDTO();
        bookDTO.setName(book.getName());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setFormattedIsbn(bookDTO.formatIsbn(book.getIsbn()));
    }

    @Test
    @Order(1)
    public void saveBook() throws Exception {
        Mockito.when(bookService.create(Mockito.any(Book.class))).thenReturn(bookDTO);

        mockMvc.perform(
                        post("/api/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(book))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(bookDTO.getName())))
                .andExpect(jsonPath("$.genre", is(bookDTO.getGenre())))
                .andExpect(jsonPath("$.description", is(bookDTO.getDescription())))
                .andExpect(jsonPath("$.author", is(bookDTO.getAuthor())))
                .andExpect(jsonPath("$.formattedIsbn", is(bookDTO.getFormattedIsbn())));
    }

    @Test
    @Order(2)
    public void updateBook() throws Exception {

    }

    @Test
    @Order(3)
    public void getBooks() throws Exception {

        List<BookDTO> books = new ArrayList<>();
        books.add(bookDTO);
        books.add(new BookDTO("avriv", "avhar", "argvaorg", "agnarn", "943-4-8394-8301-5"));

        Mockito.when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(
                        get("/api/book/public/all")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()", is(books.size())));
        assertThat(books).isNotEmpty();
        System.out.println(books);
    }
}
