package com.libraryManagement.backend.mapper;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.entity.Books;

public class BooksMapper {

    public static BooksOutDto mapToBooksDto(Books books) {
        BooksOutDto booksOutDto = new BooksOutDto();
        booksOutDto.setBookTitle(books.getBookTitle());
        booksOutDto.setBookAuthor(books.getBookAuthor());
        booksOutDto.setBookRating(books.getBookRating());
        booksOutDto.setBookCount(books.getBookCount());
        booksOutDto.setBookImg(books.getBookImg());

        return booksOutDto;
    }

    public static Books mapToBooksEntity(BooksInDto booksInDto) {
        Books books = new Books();
        books.setBookId(booksInDto.getBookId());
        books.setCategoryId(booksInDto.getCategoryId());
        books.setBookTitle(booksInDto.getBookTitle());
        books.setBookAuthor(booksInDto.getBookAuthor());
        books.setBookRating(booksInDto.getBookRating());
        books.setBookCount(booksInDto.getBookCount());
        books.setBookImg(booksInDto.getBookImg());

        return books;
    }


}
