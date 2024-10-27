package com.example.libraryService.controller;

import com.example.libraryService.config.TestConfig;
import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.entity.Loan;
import com.example.libraryService.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
@Import(TestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    private Loan loan;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book(1L, "Controller", "Service", "Ogboearb", "Repos", "5437854326384");

        loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);
    }

    @Test
    @DisplayName("Get available books")
    @Order(1)
    public void getAvailableBooks() throws Exception {
        BookDTO dto = new BookDTO(
                book.getName(),
                book.getGenre(),
                book.getDescription(),
                book.getAuthor(),
                book.getIsbn()
        );
        dto.setFormattedIsbn(dto.formatIsbn(book.getIsbn()));
        List<BookDTO> dtos = new ArrayList<>();
        dtos.add(dto);
        Mockito.when(libraryService.getAvailableBooks()).thenReturn(dtos);

        mockMvc.perform(
                        get("/api/loan/all")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].name", is(dto.getName())))
                .andExpect(jsonPath("$[0].genre", is(dto.getGenre())))
                .andExpect(jsonPath("$[0].description", is(dto.getDescription())))
                .andExpect(jsonPath("$[0].author", is(dto.getAuthor())))
                .andExpect(jsonPath("$[0].formattedIsbn", is(dto.getFormattedIsbn())));
    }

    @Test
    @DisplayName("Get loan")
    @Order(2)
    public void getLoan() throws Exception {
        Long id = book.getId();
        Mockito.when(libraryService.getLoan(Mockito.anyLong())).thenReturn(loan);

        mockMvc.perform(
                        get("/api/loan/book={id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(loan.getId().intValue())))
                .andExpect(jsonPath("$.book.id", is(loan.getBook().getId().intValue())))
                .andExpect(jsonPath("$.book.name", is(loan.getBook().getName())))
                .andExpect(jsonPath("$.book.genre", is(loan.getBook().getGenre())))
                .andExpect(jsonPath("$.book.description", is(loan.getBook().getDescription())))
                .andExpect(jsonPath("$.book.author", is(loan.getBook().getAuthor())))
                .andExpect(jsonPath("$.book.isbn", is(loan.getBook().getIsbn())))
                .andExpect(jsonPath("$.pickupTime", is(loan.getPickupTime())))
                .andExpect(jsonPath("$.returnTime", is(loan.getReturnTime())))
                .andExpect(jsonPath("$.available", is(loan.isAvailable())));
    }

    @Test
    @DisplayName("Pickup book")
    @Order(3)
    public void pickupBook() throws Exception {
        Long id = book.getId();
        int days = 10;
        Mockito.when(libraryService.pickupBook(Mockito.anyLong(), Mockito.anyInt())).then(invocationOnMock -> {
            int time = invocationOnMock.getArgument(1);
            loan.setPickupTime(LocalDate.now());
            loan.setReturnTime(LocalDate.now().plusDays(time));
            loan.setAvailable(false);
            return loan;
        });

        mockMvc.perform(
                        put("/api/loan/pickup={id}-{days}", id, days)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.pickupTime", is(loan.getPickupTime().toString())))
                .andExpect(jsonPath("$.returnTime", is(loan.getReturnTime().toString())))
                .andExpect(jsonPath("$.available", is(loan.isAvailable())));
    }

    @Test
    @DisplayName("Return book")
    @Order(4)
    public void returnBook() throws Exception {
        Long id = book.getId();
        loan.setPickupTime(LocalDate.now());
        loan.setReturnTime(LocalDate.now().plusDays(5));
        loan.setAvailable(false);
        Mockito.when(libraryService.returnBook(Mockito.anyLong())).then(invocationOnMock -> {
            loan.setPickupTime(null);
            loan.setReturnTime(null);
            loan.setAvailable(true);
            return loan;
        });

        mockMvc.perform(
                        put("/api/loan/return={id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.pickupTime", is(loan.getPickupTime())))
                .andExpect(jsonPath("$.returnTime", is(loan.getReturnTime())))
                .andExpect(jsonPath("$.available", is(loan.isAvailable())));
    }
}
