package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Issuances;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuancesRepository extends JpaRepository<Issuances, Integer> {

    Page<Issuances> findAll(Pageable pageable);

    List<Issuances> findByIssuanceType(String issuanceType);

    @Query("SELECT COUNT(u) FROM Issuances u WHERE u.issuanceType = 'Inhouse'")
    long count();

    List<Issuances> findByStatus(String status);

    @Query("SELECT i FROM Issuances i WHERE i.users.userCredential = :userCredential")
    List<Issuances> findByUserCredential(@Param("userCredential") String userCredential);

    @Query("SELECT i FROM Issuances i WHERE i.users.userId = :userId")
    List<Issuances> findByUserId(@Param("userId") int userId);

    @Query("SELECT i FROM Issuances i WHERE i.books.bookId = :bookId")
    List<Issuances> findByBookId(int bookId);

    @Query("SELECT i FROM Issuances i WHERE i.users.userCredential LIKE :keyword OR i.books.bookTitle LIKE :keyword")
    List<Issuances> findByUserCredentialOrBookTitleContaining(@Param("keyword") String keyword);

}
