package com.example.libraryService.service;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.mapper.BookMapper;
import com.example.libraryService.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookMapper bookMapper;

    public BookDTO create(Book book) {
        if (book.getName() == null ||
                book.getGenre() == null ||
                book.getDescription() == null ||
                book.getAuthor() == null ||
                book.getIsbn() == null ||
                !book.isValidIsbn(book.getIsbn())
        )
            return null;

        Book newBook = bookRepository.save(book);
        libraryService.create(newBook);
        return bookMapper.bookToBookDto(newBook);
    }

    public BookDTO update(Long id, Book book) {
        Optional<Book> oldBook = bookRepository.findById(id);

        if (oldBook.isEmpty())
            return null;

        if (book.getName() == null)
            book.setName(oldBook.get().getName());

        if (book.getGenre() == null)
            book.setGenre(oldBook.get().getGenre());

        if (book.getAuthor() == null)
            book.setAuthor(oldBook.get().getAuthor());

        if (book.getDescription() == null)
            book.setDescription(oldBook.get().getDescription());

        if (book.getIsbn() == null || !book.isValidIsbn(book.getIsbn()))
            book.setIsbn(oldBook.get().getIsbn());

        Book newBook = bookRepository.save(book);
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
}
