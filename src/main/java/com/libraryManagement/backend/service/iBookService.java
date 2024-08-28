package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.entity.Books;

import java.util.List;
import java.util.Optional;


public interface iBookService {
    List<BooksOutDto> findAll();

    List<BooksOutDto> findByCategoryName(String categoryName);

    BooksOutDto findById(int bookId);

    BooksOutDto findByBookTitle(String bookTitle);

    BooksOutDto findByBookAuthor(String bookAuthor);

    BooksOutDto saveBooks(BooksInDto booksInDto);

    BooksOutDto updateBooks(int bookId, BooksInDto booksInDto);

    BooksOutDto updateBooksByTitle(String bookTitle, BooksInDto booksInDto);

    void deleteById(int bookId);

    void deleteByBookTitle(String bookTitle);
}
