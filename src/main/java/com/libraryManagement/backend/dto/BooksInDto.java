package com.libraryManagement.backend.dto;
import com.libraryManagement.backend.entity.Categories;
import lombok.Data;

@Data
public class BooksInDto {

    private int bookId;

    private Categories categoryId;

    private String bookTitle;

    private String bookAuthor;

    private int bookRating;

    private int bookCount;
}
