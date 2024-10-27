package com.example.libraryService.repository;

import com.example.libraryService.entity.Book;
import com.example.libraryService.entity.Loan;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LibraryRepositoryTest {
    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    BookRepository bookRepository;

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

        loan = new Loan(1L, book, LocalDate.now(), LocalDate.now().plusDays(14)
        );
    }

    @Test
    @DisplayName("Save")
    @Order(1)
    @Rollback(value = false)
    public void saveLoan() {
        bookRepository.save(book);
        Loan newLoan = libraryRepository.save(loan);

        Assertions.assertThat(newLoan.getId()).isGreaterThan(0);
        System.out.println(newLoan);
    }

    @Test
    @DisplayName("Find by id")
    @Order(2)
    public void findById() {
        Long id = 1L;
        Loan loan = libraryRepository.findById(id).get();

        System.out.println(loan);
        Assertions.assertThat(loan.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Get all")
    @Order(3)
    public void getAll() {
        List<Loan> loans = libraryRepository.findAll();

        System.out.println(loans);
        Assertions.assertThat(loans.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Delete")
    @Order(4)
    @Rollback(value = false)
    public void deleteLoan() {
        Long id = 1L;
        libraryRepository.deleteById(id);

        Optional<Loan> loan = libraryRepository.findById(id);
        Assertions.assertThat(loan).isEmpty();
    }
}
