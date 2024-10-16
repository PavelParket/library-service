package com.example.libraryService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;

    private String name;

    private String genre;

    private String description;

    private String author;

    public boolean isValidIsbn(String isbn) {
        return isbn.length() == 13;
    }
}
