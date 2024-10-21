package com.example.libraryService.repository;

import com.example.libraryService.entity.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTests {
    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setName("Book test");
        book.setGenre("Tests");
        book.setDescription("Fireanbaerogeaoe");
        book.setAuthor("Author Test");
        book.setIsbn("9783161484104");
    }

    @Test
    @DisplayName("Save book")
    @Order(1)
    @Rollback(value = false)
    public void saveBook() {
        bookRepository.save(book);

        System.out.println(book);
        Assertions.assertThat(book.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Update book")
    @Order(2)
    @Rollback(value = false)
    public void updateBook() {
        Book book = bookRepository.findById(1L).get();
        book.setName("Update Book");
        Book newBook = bookRepository.save(book);

        System.out.println(newBook);
        Assertions.assertThat(newBook.getName()).isEqualTo("Update Book");
    }

    @Test
    @DisplayName("Get book by id")
    @Order(3)
    public void getBookById() {
        Book book = bookRepository.findById(1L).get();

        System.out.println(book);
        Assertions.assertThat(book.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Get book by isbn")
    @Order(4)
    public void getBookByIsbn() {
        String isbn = "9783161484104";
        Book book = bookRepository.findByIsbn(isbn).get();

        System.out.println(book);
        Assertions.assertThat(book.getIsbn()).isEqualTo("9783161484104");
    }

    @Test
    @DisplayName("Get all books")
    @Order(5)
    public void getBooks() {
        List<Book> books = bookRepository.findAll();

        System.out.println(books);
        Assertions.assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Delete book")
    @Order(6)
    @Rollback(value = false)
    public void deleteBook() {
        bookRepository.deleteById(1L);

        Optional<Book> book = bookRepository.findById(1L);
        Assertions.assertThat(book).isEmpty();
    }
}
