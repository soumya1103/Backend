package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

    @Query("SELECT b FROM Books b JOIN b.categoryId c WHERE c.categoryName = :categoryName")
    List<Books> findByCategoryName(@Param("categoryName") String categoryName);

    Optional<Books> findByBookTitle(String bookTitle);

    Optional<Books> findByBookAuthor(String bookAuthor);

    void deleteByBookTitle(String bookTitle);
}
