package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.BooksInDto;
import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.mapper.BooksMapper;
import com.libraryManagement.backend.repository.BooksRepository;
import com.libraryManagement.backend.service.iBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements iBookService {

    private BooksRepository booksRepository;

    @Autowired
    public BookServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public List<BooksOutDto> findAll() {
        List<BooksOutDto> booksOutDto = booksRepository.findAll()
                .stream().map(BooksMapper::mapToBooksDto).toList();

        return booksOutDto;
    }

    @Override
    public Optional<BooksOutDto> findById(int bookId) {
        Optional<BooksOutDto> booksOutDto = Optional.ofNullable(booksRepository.findById(bookId)
                .map(BooksMapper::mapToBooksDto)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId)));

        return booksOutDto;
    }

    @Override
    public List<BooksOutDto> findByCategoryName(String categoryName) {
        List<Books> books = booksRepository.findByCategoryName(categoryName);

        return books.stream().map(BooksMapper::mapToBooksDto).toList();
    }

    @Override
    public Optional<BooksOutDto> findByBookTitle(String bookTitle) {
        Optional<Books> optionalBook = booksRepository.findByBookTitle(bookTitle);

        return optionalBook.map(BooksMapper::mapToBooksDto);
    }

    @Override
    public Optional<BooksOutDto> findByBookAuthor(String bookAuthor) {
        Optional<Books> optionalBook = booksRepository.findByBookAuthor(bookAuthor);

        return optionalBook.map(BooksMapper::mapToBooksDto);
    }

    @Override
    public Books save(Books books) {
        return booksRepository.save(books);
    }

    @Override
    public BooksOutDto updateBooks(BooksInDto booksInDto) {
        Optional<Books> books = booksRepository.findById(booksInDto.getBookId());

        if(!books.isPresent()) {
            throw new RuntimeException("Book not found");
        }

        Books booksToUpdate = books.get();

        if (booksInDto.getBookTitle() != null && !booksInDto.getBookTitle().isEmpty()) {
            booksToUpdate.setBookTitle(booksInDto.getBookTitle());
        }

        if (booksInDto.getBookAuthor() != null && !booksInDto.getBookAuthor().isEmpty()) {
            booksToUpdate.setBookAuthor(booksInDto.getBookAuthor());
        }

        if (booksInDto.getBookRating() != 0) {
            booksToUpdate.setBookRating(booksInDto.getBookRating());
        }

        if (booksInDto.getBookCount() != 0) {
            booksToUpdate.setBookCount(booksInDto.getBookCount());
        }

        if (booksInDto.getBookImg() != null && !booksInDto.getBookImg().isEmpty()) {
            booksToUpdate.setBookImg(booksInDto.getBookImg());
        }

        Books updatedBooks = booksRepository.save(booksToUpdate);

        BooksOutDto booksOutDto = new BooksOutDto();
        booksOutDto.setBookTitle(updatedBooks.getBookTitle());
        booksOutDto.setBookAuthor(updatedBooks.getBookAuthor());
        booksOutDto.setBookRating(updatedBooks.getBookRating());
        booksOutDto.setBookCount(updatedBooks.getBookCount());
        booksOutDto.setBookImg(updatedBooks.getBookImg());

        return booksOutDto;
    }

    @Override
    public void deleteById(int bookId) {
        booksRepository.deleteById(bookId);

    }
}


