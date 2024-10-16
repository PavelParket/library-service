package com.example.libraryService.Service;

import com.example.libraryService.DTO.BookDTO;
import com.example.libraryService.Entity.Book;
import com.example.libraryService.Mapper.BookMapper;
import com.example.libraryService.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public BookDTO create(Book book) {
        if (book.getName().isEmpty() ||
                book.getGenre().isEmpty() ||
                book.getDescription().isEmpty() ||
                book.getAuthor().isEmpty() ||
                book.getIsbn().isEmpty() ||
                book.isValidIsbn(book.getIsbn())
        )
            return null;

        bookRepository.save(book);
        return bookMapper.bookToBookDto(bookRepository.findById(book.getId()).orElseThrow(IllegalArgumentException::new));
    }

    public BookDTO update(Book book) {
        Optional<Book> oldBook = bookRepository.findById(book.getId());

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

        if (book.getIsbn() == null || book.isValidIsbn(book.getIsbn()))
            book.setIsbn(oldBook.get().getIsbn());

        bookRepository.save(book);
        return bookMapper.bookToBookDto(bookRepository.findById(book.getId()).orElseThrow(IllegalArgumentException::new));
    }
}
