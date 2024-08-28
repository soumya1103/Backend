package com.libraryManagement.backend.mapper;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Categories;

public class BooksMapper {

    public static BooksOutDto mapToBooksDto(Books books) {
        BooksOutDto booksOutDto = new BooksOutDto();
        booksOutDto.setBookId(books.getBookId());
        booksOutDto.setBookTitle(books.getBookTitle());
        booksOutDto.setBookAuthor(books.getBookAuthor());
        booksOutDto.setBookRating(books.getBookRating());
        booksOutDto.setBookCount(books.getBookCount());
        booksOutDto.setCategoryName(books.getCategoryId().getCategoryName());

        return booksOutDto;
    }

    public static Books mapToBooksEntity(BooksInDto booksInDto, Categories category) {
        Books books = new Books();
        books.setBookId(booksInDto.getBookId());
        books.setCategoryId(category);
        books.setBookTitle(booksInDto.getBookTitle());
        books.setBookAuthor(booksInDto.getBookAuthor());
        books.setBookRating(booksInDto.getBookRating());
        books.setBookCount(booksInDto.getBookCount());

        return books;
    }
}
