package com.example.libraryService.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "pickup_time")
    private LocalDate pickupTime;

    @Column(name = "return_time")
    private LocalDate returnTime;

    @Column(name = "available")
    @ColumnDefault("true")
    private boolean available;

    public Loan() {
        this.available = true;
    }

    public Loan(Long id, Book book, LocalDate pickupTime, LocalDate returnTime) {
        this.id = id;
        this.book = book;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.available = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDate pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDate getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDate returnTime) {
        this.returnTime = returnTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book.getName() +
                ", pickupTime=" + pickupTime +
                ", returnTime=" + returnTime +
                ", available=" + available +
                '}';
    }
}
