package com.libraryManagement.backend.mapper;

import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Issuances;

public class IssuancesMapper {

    public static IssuancesOutDto mapToIssuances(IssuancesOutDto issuancesDto, Issuances issuances) {

        issuancesDto.setIssueDate(issuances.getIssueDate());
        issuancesDto.setReturnDate(issuances.getReturnDate());
        issuancesDto.setStatus(issuances.getStatus());
        issuancesDto.setIssuanceType(issuances.getIssuanceType());

        return issuancesDto;
    }

    public static Issuances mapToIssuancesDto(Issuances issuances, IssuancesOutDto issuancesDto) {

        issuances.setIssueDate(issuancesDto.getIssueDate());
        issuances.setReturnDate(issuancesDto.getReturnDate());
        issuances.setStatus(issuancesDto.getStatus());
        issuances.setIssuanceType(issuancesDto.getIssuanceType());

        return issuances;
    }
}
