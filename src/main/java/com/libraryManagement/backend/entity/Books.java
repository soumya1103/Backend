package com.libraryManagement.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categoryId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_rating")
    private int bookRating;

    @Column(name = "book_count")
    private int bookCount;

}
