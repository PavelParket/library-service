package com.example.libraryService.Controller;

import com.example.libraryService.DTO.BookDTO;
import com.example.libraryService.Entity.Book;
import com.example.libraryService.Service.BookService;
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

    @PutMapping("/c")
    public ResponseEntity<?> create(@RequestBody Book book) {
        BookDTO newBook = bookService.create(book);

        if (newBook == null)
            return new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @PutMapping("/u")
    public ResponseEntity<?> update(@RequestBody Book book) {
        BookDTO newBook = bookService.update(book);
        return newBook == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return bookService.delete(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<BookDTO> books = bookService.getAll();
        return books == null ?
                new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BookDTO book = bookService.getById(id);
        return book == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/isbn")
    public ResponseEntity<?> getByISBN(@PathVariable String isbn) {
        BookDTO book = bookService.getByISBN(isbn);
        return book == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(book, HttpStatus.OK);
    }
}
