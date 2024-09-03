package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Issuances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuancesRepository extends JpaRepository<Issuances, Integer> {
    List<Issuances> findByIssuanceType(String issuanceType);

    @Query("SELECT COUNT(u) FROM Issuances u WHERE u.issuanceType = 'Inhouse'")
    long count();

    List<Issuances> findByStatus(String status);

    @Query("SELECT i FROM Issuances i WHERE i.users.userCredential = :userCredential")
    List<Issuances> findByUserCredential(@Param("userCredential") String userCredential);

//    List<Issuances> findByUserId(Users userId);
//
//    List<Issuances> findByBookId(Books bookId);
//
//    Issuances findByUserCredential(@Param("userCredential") String userCredential);
}
