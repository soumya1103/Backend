package com.libraryManagement.backend.dto;

import lombok.Data;

@Data
public class BooksOutDto {

    private int bookId;

    private String bookTitle;

    private String bookAuthor;

    private int bookRating;

    private int bookCount;

    private String categoryName;
}
