package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;

import java.util.List;

public interface iIssuancesService {

    List<IssuancesOutDto> findAll();

    IssuancesOutDto findById(int issuanceId);

    IssuancesOutDto saveIssuances(IssuancesInDto issuancesInDto);

    IssuancesOutDto updateIssuance(int issuanceId, IssuancesInDto issuancesInDto);

    void deleteById(int issuanceId);

    List<IssuancesOutDto> getIssuanceByIssuanceType(String issuanceType);

    List<IssuancesOutDto> getIssuanceByStatus(String status);

    long getIssuanceCountByType();

    List<IssuancesOutDto> getIssuanceByUserCredential(String userCredential);
}
