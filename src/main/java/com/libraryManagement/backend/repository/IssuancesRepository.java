package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Issuances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuancesRepository extends JpaRepository<Issuances, Integer> {
}
