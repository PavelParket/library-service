package com.example.libraryService.dto;

public class BookDTO {
    private String name;

    private String genre;

    private String description;

    private String author;

    private String formattedIsbn;

    public BookDTO(String name, String genre, String description, String author, String formattedIsbn) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.author = author;
        this.formattedIsbn = formattedIsbn;
    }

    public BookDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFormattedIsbn() {
        return formattedIsbn;
    }

    public void setFormattedIsbn(String formattedIsbn) {
        this.formattedIsbn = formattedIsbn;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", formattedIsbn='" + formattedIsbn + '\'' +
                '}';
    }

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
