package com.example.libraryService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO {
    private String name;

    private String genre;

    private String description;

    private String author;

    private String formatedIsbn;

    public String formatIsbn(String isbn) {
        return String.format(
                "%s-%s-%s-%s-%s",
                isbn.substring(0, 3),
                isbn.charAt(3),
                isbn.substring(4, 8),
                isbn.substring(8, 12),
                isbn.charAt(12)
        );
    }
}
