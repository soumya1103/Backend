package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Categories;
import com.libraryManagement.backend.exception.ResourceAlreadyExistsException;
import com.libraryManagement.backend.exception.ResourceNotFoundException;
import com.libraryManagement.backend.mapper.BooksMapper;
import com.libraryManagement.backend.repository.BooksRepository;
import com.libraryManagement.backend.repository.CategoriesRepository;
import com.libraryManagement.backend.repository.IssuancesRepository;
import com.libraryManagement.backend.service.iBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements iBookService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    private final IssuancesRepository issuancesRepository;

    public BookServiceImpl (IssuancesRepository issuancesRepository) {
        this.issuancesRepository = issuancesRepository;
    }

    @Override
    public Page<BooksOutDto> getBooks(Pageable pageable) {
        Page<Books> booksPage;
        booksPage = booksRepository.findAll(pageable);

        return booksPage.map(BooksMapper::mapToBooksDto);
    }

    @Override
    public BooksOutDto findById(int bookId) {
        Books books = booksRepository.findById(bookId).orElseThrow(
                () -> new RuntimeException("Book not found with id: " + bookId)
        );

        BooksOutDto booksOutDto = BooksMapper.mapToBooksDto(books);
        return booksOutDto;
    }

    @Override
    public List<BooksOutDto> findByCategoryName(String categoryName) {
        List<Books> books = booksRepository.findByCategoryName(categoryName);

        return books.stream().map(BooksMapper::mapToBooksDto).toList();
    }

    @Override
    public BooksOutDto findByBookTitle(String bookTitle) {

        Books books = booksRepository.findByBookTitle(bookTitle).orElseThrow(
                () -> new RuntimeException("Book not found with title: " + bookTitle)
        );
        BooksOutDto booksOutDto = BooksMapper.mapToBooksDto(books);
        return booksOutDto;
    }

    @Override
    public BooksOutDto findByBookAuthor(String bookAuthor) {
        Books books = booksRepository.findByBookAuthor(bookAuthor).orElseThrow(
                () -> new RuntimeException("Book not found with author: " + bookAuthor)
        );
        BooksOutDto booksOutDto = BooksMapper.mapToBooksDto(books);

        return booksOutDto;
    }

    @Override
    @Transactional
    public Books save(BooksInDto booksInDto) {
        Books existingBook = booksRepository.findByBookTitleIgnoreCase(booksInDto.getBookTitle());

        if (existingBook != null) {
            throw new ResourceAlreadyExistsException("Duplicate entry.");
        }

        Categories category = categoriesRepository.findById(booksInDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));

        Books book = BooksMapper.mapToBooksEntity(booksInDto, category);

        return booksRepository.save(book);
    }

    @Override
    @Transactional
    public BooksOutDto updateBooks(int bookId, BooksInDto booksInDto) {
        Optional<Books> books = booksRepository.findById(bookId);
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Book not found.");
        }

        Books existingBook = books.get();

        if (booksInDto.getCategoryId() != null) {
            Categories category = categoriesRepository.findById(booksInDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
            existingBook.setCategoryId(category);
        }

        if (booksInDto.getBookTitle() != null) {
            Books existingBookTitle = booksRepository.findByBookTitleIgnoreCase(booksInDto.getBookTitle());

            if (existingBookTitle != null) {
                throw new ResourceAlreadyExistsException("Duplicate entry.");
            }

            existingBook.setBookTitle(booksInDto.getBookTitle());
        }

        if (booksInDto.getBookAuthor() != null) {
            existingBook.setBookAuthor(booksInDto.getBookAuthor());
        }

        if (booksInDto.getBookRating() != null) {
            existingBook.setBookRating(booksInDto.getBookRating());
        }

        if (booksInDto.getBookCount() != null) {
            existingBook.setBookCount(booksInDto.getBookCount());
        }

        Books updatedBook = booksRepository.save(existingBook);

        return BooksMapper.mapToBooksDto(updatedBook);
    }


    @Override
    public void deleteById(int bookId) {
        booksRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public void deleteByBookTitle(String bookTitle) {
        booksRepository.deleteByBookTitle(bookTitle);

    }

    @Override
    public List<BooksOutDto> getAllBooks() {
        List<BooksOutDto> booksOutDto = booksRepository.findAll()
                .stream().map(BooksMapper::mapToBooksDto).toList();

        return booksOutDto;
    }

    @Override
    public List<BooksOutDto> searchByBooks(String keywords) {
        List<Books> books = booksRepository.findByBookTitleOrBookAuthorContaining("%" + keywords + "%");
        return books.stream().map(BooksMapper::mapToBooksDto).toList();
    }

    @Override
    public boolean isBookIssued(int bookId) {
            return issuancesRepository.existsByBooksBookIdAndStatus(bookId, "Issued");
    }

}


