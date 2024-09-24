package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.entity.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface iBookService {
    Page<BooksOutDto> getBooks(Pageable pageable);

    List<BooksOutDto> findByCategoryName(String categoryName);

    BooksOutDto findById(int bookId);

    BooksOutDto findByBookTitle(String bookTitle);

    BooksOutDto findByBookAuthor(String bookAuthor);

    Books save(BooksInDto booksInDto);

    BooksOutDto updateBooks(int bookId, BooksInDto booksInDto);

    void deleteById(int bookId);

    List<BooksOutDto> getAllBooks();

    List<BooksOutDto> searchByBooks(String keywords);

    boolean isBookIssued(int bookId);
}
