package com.example.libraryService.controller;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/book")
@Tag(name = "Book controller", description = "Operations with books")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping
    @Operation(summary = "Create", description = "Create new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(@RequestBody Book book) {
        BookDTO newBook = bookService.create(book);
        return newBook == null ?
                new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR) :
                new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update an existing book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
        BookDTO newBook = bookService.update(id, book);
        return newBook == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete", description = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return bookService.delete(id) ?
                new ResponseEntity<>("Deleted", HttpStatus.OK) :
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/public/all")
    @Operation(summary = "Get all", description = "Retrieve all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No books found")
    })
    public ResponseEntity<?> getAll() {
        List<BookDTO> books = bookService.getAll();
        return books.isEmpty() ?
                new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    @Operation(summary = "Get by ID", description = "Retrieve a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BookDTO book = bookService.getById(id);
        return book == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/isbn={isbn}")
    @Operation(summary = "Get by ISBN", description = "Retrieve a specific book by its ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<?> getByISBN(@PathVariable String isbn) {
        BookDTO book = bookService.getByIsbn(isbn);
        return book == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(book, HttpStatus.OK);
    }
}
