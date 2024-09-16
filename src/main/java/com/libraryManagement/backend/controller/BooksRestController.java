package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.dto.response.ApiResponse;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.exception.ResourceNotAllowedToDeleteException;
import com.libraryManagement.backend.service.iBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "bookId"));
        Page<BooksOutDto> booksPage = bookService.getBooks(pageable);

        return ResponseEntity.ok(booksPage);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BooksOutDto>> getAllBooks() {
        List<BooksOutDto> booksList = bookService.getAllBooks();
        return ResponseEntity.ok(booksList);
    }


    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> findByCategoryName(@PathVariable String categoryName) {
        List<BooksOutDto> booksOutDtoList = bookService.findByCategoryName(categoryName);

        if (booksOutDtoList.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse(HttpStatus.NOT_FOUND, "Category not found."));
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
    public ResponseEntity<?> addBook(@RequestBody BooksInDto booksInDto) {
        Books books = bookService.save(booksInDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED, "Book added successfully!"));
    }

    @PutMapping("/id/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable int bookId, @RequestBody BooksInDto booksInDto) {
            BooksOutDto updatedBook = bookService.updateBooks(bookId, booksInDto);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Book updated successfully."));
    }

    @DeleteMapping("/id/{bookId}")
    public ResponseEntity<?> removeBook(@PathVariable int bookId) {
            if (bookService.isBookIssued(bookId)) {
                throw new ResourceNotAllowedToDeleteException("Book cannot be deleted as it is currently issued.");
            }
            BooksOutDto booksOutDto = bookService.findById(bookId);
            bookService.deleteById(bookId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"Book deleted successfully."));

    }

    @DeleteMapping("/title/{bookTitle}")
    public ResponseEntity<?> deleteByBookTitle(@PathVariable String bookTitle) {
        BooksOutDto booksOutDto = bookService.findByBookTitle(bookTitle);
            if (bookService.isBookIssued(booksOutDto.getBookId())) {
                throw new ResourceNotAllowedToDeleteException("Book cannot be deleted as it is currently issued.");
            }
        bookService.deleteByBookTitle(bookTitle);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"Book deleted successfully."));
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<BooksOutDto>> searchBooks(@PathVariable String keywords) {
        List<BooksOutDto> booksOutDto = bookService.searchByBooks("%" + keywords + "%");
        return ResponseEntity.ok(booksOutDto);
    }


}
