package com.example.libraryService.service;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.mapper.BookMapper;
import com.example.libraryService.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookMapper bookMapper;

    public BookDTO create(Book book) {
        if (Stream.of(book.getName(), book.getGenre(), book.getDescription(), book.getAuthor(), book.getIsbn()
        ).anyMatch(this::isNullOrEmpty)) {
            return null;
        }

        Book newBook = bookRepository.save(book);
        libraryService.create(newBook);
        return bookMapper.bookToBookDto(newBook);
    }

    public BookDTO update(Long id, Book book) {
        Optional<Book> findBook = bookRepository.findById(id);

        if (findBook.isEmpty())
            return null;

        Book oldBook = findBook.get();

        if (book.getName() != null) {
            oldBook.setName(book.getName());
        }

        if (book.getGenre() != null) {
            oldBook.setGenre(book.getGenre());
        }

        if (book.getAuthor() != null) {
            oldBook.setAuthor(book.getAuthor());
        }

        if (book.getDescription() != null) {
            oldBook.setDescription(book.getDescription());
        }

        if (book.getIsbn() != null && book.isValidIsbn(book.getIsbn()))
            oldBook.setIsbn(book.getIsbn());

        Book newBook = bookRepository.save(oldBook);
        libraryService.update(newBook);
        return bookMapper.bookToBookDto(newBook);
    }

    public boolean delete(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty())
            return false;

        libraryService.delete(id);
        bookRepository.deleteById(id);
        return true;
    }

    public List<BookDTO> getAll() {
        return bookMapper.booksToBookDtos(bookRepository.findAll());
    }

    public BookDTO getById(Long id) {
        return bookRepository.findById(id).map(e -> bookMapper.bookToBookDto(e)).orElse(null);
    }

    public BookDTO getByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).map(e -> bookMapper.bookToBookDto(e)).orElse(null);
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
