package com.example.libraryService.repository;

import com.example.libraryService.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Loan, Long> {
}
