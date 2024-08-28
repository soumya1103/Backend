package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;

import java.util.List;
import java.util.Optional;

public interface iIssuancesService {

    List<IssuancesOutDto> findAll();

    Optional<IssuancesOutDto> findById(int issuanceId);

    List<IssuancesOutDto> findByUserId(Users userId);

    List<IssuancesOutDto> findByBookId(Books bookId);

    Issuances save(Issuances issuances);

    IssuancesOutDto updateIssuance(IssuancesInDto issuancesInDto);

    void deleteById(int issuanceId);
}
