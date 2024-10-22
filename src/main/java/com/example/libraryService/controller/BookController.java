package com.example.libraryService.controller;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book) {
        BookDTO newBook = bookService.create(book);
        return newBook == null ?
                new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR) :
                new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
        BookDTO newBook = bookService.update(id, book);
        return newBook == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return bookService.delete(id) ?
                new ResponseEntity<>("Deleted", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/public/all")
    public ResponseEntity<?> getAll() {
        List<BookDTO> books = bookService.getAll();
        return books.isEmpty() ?
                new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BookDTO book = bookService.getById(id);
        return book == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/isbn={isbn}")
    public ResponseEntity<?> getByISBN(@PathVariable String isbn) {
        BookDTO book = bookService.getByIsbn(isbn);
        return book == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(book, HttpStatus.OK);
    }
}
