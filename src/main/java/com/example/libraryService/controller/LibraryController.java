package com.example.libraryService.controller;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Loan;
import com.example.libraryService.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/loan")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/all")
    public ResponseEntity<?> getAvailableBooks() {
        List<BookDTO> list = libraryService.getAvailableBooks();
        return list.isEmpty() ?
                new ResponseEntity<>("No available books", HttpStatus.OK) :
                new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/book={id}")
    public ResponseEntity<?> getLoan(@PathVariable Long id) {
        Loan loan = libraryService.getLoan(id);
        return loan == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PutMapping("/pickup={id}-{days}")
    public ResponseEntity<?> pickupBook(@PathVariable Long id, @PathVariable int days) {
        Loan loan = libraryService.pickupBook(id, days);
        return loan == null ?
                new ResponseEntity<>("Available", HttpStatus.OK) :
                new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PutMapping("/return={id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        Loan loan = libraryService.returnBook(id);
        return loan == null ?
                new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(loan, HttpStatus.OK);
    }
}
