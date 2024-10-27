package com.example.libraryService.controller;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Loan;
import com.example.libraryService.service.LibraryService;
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
@RequestMapping(value = "api/loan")
@Tag(name = "Library controller", description = "Pickup or return book, view available books, view loan info")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/all")
    @Operation(summary = "Get available books", description = "Retrieve a list of all available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available books retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No available books found")
    })
    public ResponseEntity<?> getAvailableBooks() {
        List<BookDTO> list = libraryService.getAvailableBooks();
        return list.isEmpty() ?
                new ResponseEntity<>("No available books", HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/book={id}")
    @Operation(summary = "Get loan by ID", description = "Retrieve loan information for a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public ResponseEntity<?> getLoan(@PathVariable Long id) {
        Loan loan = libraryService.getLoan(id);
        return loan == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PutMapping("/pickup={id}-{days}")
    @Operation(summary = "Pickup", description = "Pickup a book for a specified number of days")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book picked up successfully"),
            @ApiResponse(responseCode = "204", description = "Book not available for pickup")
    })
    public ResponseEntity<?> pickupBook(@PathVariable Long id, @PathVariable int days) {
        Loan loan = libraryService.pickupBook(id, days);
        return loan == null ?
                new ResponseEntity<>("Available", HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PutMapping("/return={id}")
    @Operation(summary = "Return", description = "Return a previously borrowed book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book returned successfully"),
            @ApiResponse(responseCode = "404", description = "Loan not found for the specified book ID")
    })
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        Loan loan = libraryService.returnBook(id);
        return loan == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(loan, HttpStatus.OK);
    }
}
