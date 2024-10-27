package com.example.libraryService.service;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.entity.Loan;
import com.example.libraryService.mapper.BookMapper;
import com.example.libraryService.repository.LibraryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryServiceTest {
    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private LibraryService libraryService;

    private Loan loan;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book(
                1L,
                "Test",
                "Books",
                "Oaoerigjans",
                "Author Test",
                "9783619384104"
        );

        loan = new Loan(1L, book, LocalDate.now(), LocalDate.now().plusDays(14));
    }

    @Test
    @DisplayName("Save")
    @Order(1)
    public void saveLoan() {
        Mockito.when(libraryRepository.save(Mockito.any(Loan.class))).thenReturn(loan);
        Mockito.when(libraryRepository.findAll()).thenReturn(Collections.singletonList(loan));

        libraryService.create(book);
        Assertions.assertThat(libraryRepository.findAll()).isNotEmpty();
        Assertions.assertThat(libraryRepository.findAll().getFirst().getBook()).isEqualTo(book);
        System.out.println(libraryRepository.findAll().getFirst());
    }

    @Test
    @DisplayName("Delete")
    @Order(2)
    public void deleteLoan() {
        Long id = book.getId();
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        Mockito.when(libraryRepository.findAll()).thenReturn(loans);
        Mockito.doNothing().when(libraryRepository).delete(Mockito.any(Loan.class));

        libraryService.delete(id);
        libraryRepository.findAll().remove(loan);
        Mockito.verify(libraryRepository).delete(Mockito.any(Loan.class));
        Assertions.assertThat(libraryRepository.findAll()).isEmpty();
        System.out.println(loans);
    }

    @Test
    @DisplayName("Update")
    @Order(3)
    public void updateLoan() {
        Long id = book.getId();
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        Book newBook = new Book(1L, "Ovnren", "voarn", "vaorhvn", "aievnv", "1136852196881");
        Mockito.when(libraryRepository.findAll()).thenReturn(loans);
        Mockito.when(libraryRepository.save(Mockito.any(Loan.class))).thenReturn(null);

        libraryService.update(newBook);
        Mockito.verify(libraryRepository).save(loan);
    }

    @Test
    @DisplayName("Get available books")
    @Order(4)
    public void getAvailableBooks() {
        BookDTO bookDTO = new BookDTO("Test", "Books", "Oaoerigjans", "Author Test", "978-3-6193-8410-4");
        Book book1 = new Book(2L, "Book2", "UIvar", "Oaeavhveaor", "Ioaurvr", "9431452787652");
        BookDTO bookDTO1 = new BookDTO("Book2", "UIvar", "Oaeavhveaor", "Ioaurvr", "943-1-4527-8765-2");
        Loan loan1 = new Loan(2L, book1, LocalDate.now(), LocalDate.now().plusDays(10));
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        loans.add(loan1);
        List<BookDTO> books = new ArrayList<>();
        books.add(bookDTO);
        books.add(bookDTO1);
        Mockito.when(libraryRepository.findAll()).thenReturn(loans);
        Mockito.when(bookMapper.booksToBookDtos(Mockito.anyList())).thenReturn(books);

        List<BookDTO> dtos = libraryService.getAvailableBooks();
        Assertions.assertThat(dtos).isNotEmpty();
        System.out.println(loans);
        System.out.println(dtos);
    }

    @Test
    @DisplayName("Get loan")
    @Order(5)
    public void getLoan() {
        Long id = book.getId();
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        Mockito.when(libraryRepository.findAll()).thenReturn(loans);

        Loan loan = libraryService.getLoan(id);
        Assertions.assertThat(loan).isNotNull();
        Assertions.assertThat(loan.getBook().getId()).isEqualTo(id);
        System.out.println(loan);
    }

    @Test
    @DisplayName("Pickup book")
    @Order(6)
    public void pickupBook() {
        Long id = book.getId();
        int days = 10;
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        Mockito.when(libraryRepository.findAll()).thenReturn(loans);
        Mockito.when(libraryRepository.save(Mockito.any(Loan.class))).then(invocationOnMock -> invocationOnMock.<Loan>getArgument(0));

        Loan loan = libraryService.pickupBook(id, days);
        Assertions.assertThat(loan).isNotNull();
        Assertions.assertThat(loan.getPickupTime()).isEqualTo(LocalDate.now());
        Assertions.assertThat(loan.getReturnTime()).isEqualTo(LocalDate.now().plusDays(days));
        Assertions.assertThat(loan.isAvailable()).isFalse();
        System.out.println(loan);
    }

    @Test
    @DisplayName("Return book")
    @Order(7)
    public void returnBook() {
        Long id = book.getId();
        loan.setAvailable(false);
        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        Mockito.when(libraryRepository.findAll()).thenReturn(loans);
        Mockito.when(libraryRepository.save(Mockito.any(Loan.class))).then(invocationOnMock -> invocationOnMock.<Loan>getArgument(0));

        Loan loan = libraryService.returnBook(id);
        Assertions.assertThat(loan).isNotNull();
        Assertions.assertThat(loan.getPickupTime()).isNull();
        Assertions.assertThat(loan.getReturnTime()).isNull();
        Assertions.assertThat(loan.isAvailable()).isTrue();
        System.out.println(loan);
    }
}
