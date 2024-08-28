package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.service.iBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lms/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BooksRestController {
    private final iBookService bookService;

    @GetMapping("")
    public List<BooksOutDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<BooksOutDto>> findByCategoryName(@PathVariable String categoryName) {
        List<BooksOutDto> booksOutDtoList = bookService.findByCategoryName(categoryName);

        if (booksOutDtoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(booksOutDtoList);
        }
    }

    @GetMapping("/id/{bookId}")
    public ResponseEntity<BooksOutDto> getBooks(@PathVariable int bookId) {
        BooksOutDto booksOutDto = bookService.findById(bookId);

        return ResponseEntity.ok(booksOutDto);
    }

    @GetMapping("/title/{bookTitle}")
    public ResponseEntity<BooksOutDto> findByBookTitle(@PathVariable String bookTitle) {
        BooksOutDto booksOutDto = bookService.findByBookTitle(bookTitle);

        return ResponseEntity.ok(booksOutDto);
    }

    @GetMapping("/author/{bookAuthor}")
    public ResponseEntity<BooksOutDto> findByBookAuthor(@PathVariable String bookAuthor) {
        BooksOutDto booksOutDtoList = bookService.findByBookAuthor(bookAuthor);

        return ResponseEntity.ok(booksOutDtoList);
    }

    @PostMapping("")
    public ResponseEntity<BooksOutDto> addBook(@RequestBody BooksInDto booksInDto) {
        BooksOutDto booksOutDto = bookService.saveBooks(booksInDto);
        return ResponseEntity.ok(booksOutDto);
    }

    @PutMapping("/id/{bookId}")
    public ResponseEntity<BooksOutDto> updateBook(@PathVariable int bookId, @RequestBody BooksInDto booksInDto) {
        BooksOutDto updatedBook = bookService.updateBooks(bookId, booksInDto);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/title/{bookTitle}")
    public ResponseEntity<BooksOutDto> updateBook(@PathVariable String bookTitle, @RequestBody BooksInDto booksInDto) {
        BooksOutDto updatedBook = bookService.updateBooksByTitle(bookTitle, booksInDto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/id/{bookId}")
    public String removeBook(@PathVariable int bookId) {

        BooksOutDto booksOutDto = bookService.findById(bookId);

        bookService.deleteById(bookId);

        return "Deleted book id: " + bookId;
    }

    @DeleteMapping("/title/{bookTitle}")
    public String deleteByBookTitle(@PathVariable String bookTitle) {

        BooksOutDto booksOutDto = bookService.findByBookTitle(bookTitle);

        bookService.deleteByBookTitle(bookTitle);

        return "Deleted book title: " + bookTitle;

    }



}
