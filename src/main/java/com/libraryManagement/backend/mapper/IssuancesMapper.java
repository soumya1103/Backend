package com.libraryManagement.backend.mapper;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Issuances;

public class IssuancesMapper {

    public static IssuancesOutDto mapToIssuancesDto(Issuances issuances) {

        IssuancesOutDto issuancesOutDto = new IssuancesOutDto();
        issuancesOutDto.setIssueDate(issuances.getIssueDate());
        issuancesOutDto.setReturnDate(issuances.getReturnDate());
        issuancesOutDto.setStatus(issuances.getStatus());
        issuancesOutDto.setIssuanceType(issuances.getIssuanceType());

        return issuancesOutDto;
    }

    public static Issuances mapToIssuancesEntity(IssuancesInDto issuancesInDto) {

        Issuances issuances = new Issuances();
        issuances.setUserId(issuancesInDto.getUserId());
        issuances.setBookId(issuancesInDto.getBookId());
        issuances.setIssueDate(issuancesInDto.getIssueDate());
        issuances.setReturnDate(issuancesInDto.getReturnDate());
        issuances.setStatus(issuancesInDto.getStatus());
        issuances.setIssuanceType(issuancesInDto.getIssuanceType());

        return issuances;
    }
}
