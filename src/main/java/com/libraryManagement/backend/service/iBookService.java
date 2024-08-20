package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.entity.Books;

import java.util.List;
import java.util.Optional;


public interface iBookService {
    List<BooksOutDto> findAll();

    List<BooksOutDto> findByCategoryName(String categoryName);

    Optional<BooksOutDto> findById(int bookId);

    Optional<BooksOutDto> findByBookTitle(String bookTitle);

    Optional<BooksOutDto> findByBookAuthor(String bookAuthor);

    Books save(Books books);

    BooksOutDto updateBooks(BooksInDto booksInDto);

    void deleteById(int bookId);
}
