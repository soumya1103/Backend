package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuancesRepository extends JpaRepository<Issuances, Integer> {

    List<Issuances> findByUserId(Users userId);

    List<Issuances> findByBookId(Books bookId);
}
