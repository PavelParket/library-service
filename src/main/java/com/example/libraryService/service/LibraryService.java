package com.example.libraryService.service;

import com.example.libraryService.dto.BookDTO;
import com.example.libraryService.entity.Book;
import com.example.libraryService.entity.Loan;
import com.example.libraryService.mapper.BookMapper;
import com.example.libraryService.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookMapper bookMapper;

    @Async
    public void create(Book book) {
        Loan loan = new Loan();
        loan.setBook(book);

        libraryRepository.save(loan);
    }

    public void delete(Long bookId) {
        libraryRepository.findAll().stream()
                .filter(e -> e.getBook().getId().equals(bookId))
                .findFirst()
                .ifPresent(loan -> libraryRepository.delete(loan));
    }

    public List<BookDTO> getAvailableBooks() {
        return bookMapper.booksToBookDtos(
                libraryRepository.findAll().stream()
                        .filter(Loan::isAvailable)
                        .map(Loan::getBook)
                        .toList()
        );
    }

    public Loan getLoan(Long bookId) {
        return libraryRepository.findAll().stream()
                .filter(e -> e.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);
    }

    public Loan pickupBook(Long bookId, int days) {
        Loan loan = libraryRepository.findAll().stream()
                .filter(e -> e.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);

        if (loan == null || !loan.isAvailable())
            return null;

        loan.setPickupTime(LocalDate.now());
        loan.setReturnTime(LocalDate.now().plusDays(days));
        loan.setAvailable(false);
        return libraryRepository.save(loan);
    }

    public Loan returnBook(Long bookId) {
        Loan loan = libraryRepository.findAll().stream()
                .filter(e -> e.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);

        if (loan == null || loan.isAvailable())
            return null;

        loan.setPickupTime(null);
        loan.setReturnTime(null);
        loan.setAvailable(true);
        return libraryRepository.save(loan);
    }
}
