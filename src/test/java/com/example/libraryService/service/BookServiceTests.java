package com.example.libraryService.service;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.mapper.BookMapper;
import com.example.libraryService.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

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
    public void saveBook() {
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(bookMapper.bookToBookDto(book)).thenReturn(bookDTO);

        BookDTO savedBook = bookService.create(book);

        System.out.println(savedBook);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getName()).isEqualTo(bookDTO.getName());
    }

    @Test
    @Order(2)
    public void updateBook() {
        Long id = 1L;
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Book newBook = new Book();
        newBook.setName("Updated Book");
        newBook.setGenre("veairjoe");

        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(newBook);

        Mockito.when(bookMapper.bookToBookDto(any(Book.class))).thenAnswer(invocationOnMock -> {
            Book bookToMap = invocationOnMock.getArgument(0);
            BookDTO dto = new BookDTO();
            dto.setName(bookToMap.getName());
            dto.setGenre(bookToMap.getGenre());
            dto.setDescription(bookToMap.getDescription());
            dto.setAuthor(bookToMap.getAuthor());
            dto.setFormattedIsbn(dto.formatIsbn(bookToMap.getIsbn()));
            return dto;
        });

        System.out.println(book);

        BookDTO updatedBook = bookService.update(id, newBook);

        System.out.println(updatedBook);
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getName()).isEqualTo(newBook.getName());
        assertThat(updatedBook.getGenre()).isEqualTo(newBook.getGenre());
        assertThat(updatedBook.getDescription()).isEqualTo(bookDTO.getDescription());
        assertThat(updatedBook.getAuthor()).isEqualTo(bookDTO.getAuthor());
        assertThat(updatedBook.getFormattedIsbn()).isEqualTo(bookDTO.getFormattedIsbn());
    }

    @Test
    @Order(3)
    public void getBooks() {
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(new Book(2L, "Second Book", "ergnvre", "feaorinve", "foaiejear", "3218649721563"));

        List<BookDTO> dtos = books.stream()
                .map(e -> {
                    BookDTO dto = new BookDTO();
                    dto.setName(e.getName());
                    dto.setGenre(e.getGenre());
                    dto.setDescription(e.getDescription());
                    dto.setAuthor(e.getAuthor());
                    dto.setFormattedIsbn(dto.formatIsbn(e.getIsbn()));
                    return dto;
                })
                .toList();

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(bookMapper.booksToBookDtos(books)).thenReturn(dtos);

        List<BookDTO> result = bookService.getAll();

        System.out.println(result);
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(dtos.size());
        assertThat(result).isEqualTo(dtos);
        System.out.println("Successful");
    }

    @Test
    @Order(4)
    public void getById() {
        Long id = book.getId();
        Mockito.when(bookRepository.findById(id)).thenAnswer(invocationOnMock -> {
            if (id.equals(book.getId()))
                return Optional.of(book);
            else
                return Optional.empty();
        });
        Mockito.when(bookMapper.bookToBookDto(book)).thenReturn(bookDTO);

        BookDTO getBook = bookService.getById(id);

        System.out.println(getBook);
        assertThat(getBook).isNotNull();
        assertThat(getBook.getName()).isEqualTo(bookDTO.getName());
        assertThat(getBook.getGenre()).isEqualTo(bookDTO.getGenre());
        assertThat(getBook.getDescription()).isEqualTo(bookDTO.getDescription());
        assertThat(getBook.getAuthor()).isEqualTo(bookDTO.getAuthor());
        assertThat(getBook.getFormattedIsbn()).isEqualTo(bookDTO.getFormattedIsbn());
        System.out.println("Successful");
    }

    @Test
    @Order(5)
    public void getByIsbn() {
        String isbn = book.getIsbn();
        Mockito.when(bookRepository.findByIsbn(isbn)).thenAnswer(invocationOnMock -> {
            if (isbn.equals(book.getIsbn()))
                return Optional.of(book);
            else
                return Optional.empty();
        });
        Mockito.when(bookMapper.bookToBookDto(book)).thenReturn(bookDTO);

        BookDTO getBook = bookService.getByIsbn(isbn);

        System.out.println(getBook);
        assertThat(getBook).isNotNull();
        assertThat(getBook.getName()).isEqualTo(bookDTO.getName());
        assertThat(getBook.getGenre()).isEqualTo(bookDTO.getGenre());
        assertThat(getBook.getDescription()).isEqualTo(bookDTO.getDescription());
        assertThat(getBook.getAuthor()).isEqualTo(bookDTO.getAuthor());
        assertThat(getBook.getFormattedIsbn()).isEqualTo(bookDTO.getFormattedIsbn());
        System.out.println("Successful");
    }

    @Test
    @Order(6)
    public void deleteBook() {
        Long id = book.getId();
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.doNothing().when(bookRepository).deleteById(id);

        boolean result = bookService.delete(id);

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(id);
        assertTrue(result);
        System.out.println("Successful");
    }
}
