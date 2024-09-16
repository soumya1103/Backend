package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iIssuancesService {

    Page<IssuancesOutDto> getIssuances(Pageable pageable);

    IssuancesOutDto findById(int issuanceId);

    IssuancesOutDto saveIssuances(IssuancesInDto issuancesInDto);

    IssuancesOutDto updateIssuance(int issuanceId, IssuancesInDto issuancesInDto);

    void deleteById(int issuanceId);

    List<IssuancesOutDto> getIssuanceByIssuanceType(String issuanceType);

    long getIssuanceCountByType();

    List<IssuancesOutDto> getIssuanceByUserCredential(String userCredential);

    List<IssuancesOutDto> findByBookId(int bookId);

    List<IssuancesOutDto> findByUserId(int userId);

    List<IssuancesOutDto> searchByCredential(String keywords);
}
