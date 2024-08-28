package com.libraryManagement.backend.dto;

import lombok.Data;

@Data
public class BooksInDto {

    private int bookId;

    private Integer categoryId;

    private String bookTitle;

    private String bookAuthor;

    private Integer bookRating;

    private Integer bookCount;
}
