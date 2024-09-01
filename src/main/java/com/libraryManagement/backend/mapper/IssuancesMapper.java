package com.libraryManagement.backend.mapper;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;

public class IssuancesMapper {

    public static IssuancesOutDto mapToIssuancesDto(Issuances issuances) {

        IssuancesOutDto issuancesOutDto = new IssuancesOutDto();
        issuancesOutDto.setIssuanceId(issuances.getIssuanceId());
        issuancesOutDto.setIssueDate(issuances.getIssueDate());
        issuancesOutDto.setReturnDate(issuances.getReturnDate());
        issuancesOutDto.setStatus(issuances.getStatus());
        issuancesOutDto.setIssuanceType(issuances.getIssuanceType());
        issuancesOutDto.setBookTitle(issuances.getBooks().getBookTitle());
        issuancesOutDto.setUserCredential(issuances.getUsers().getUserCredential());
        issuancesOutDto.setUserName(issuances.getUsers().getUserName());

        return issuancesOutDto;
    }

    public static Issuances mapToIssuancesEntity(IssuancesInDto issuancesInDto, Books books, Users users) {

        Issuances issuances = new Issuances();
        issuances.setIssuanceId(issuancesInDto.getIssuanceId());
        issuances.setUsers(users);
        issuances.setBooks(books);
        issuances.setIssueDate(issuancesInDto.getIssueDate());
        issuances.setReturnDate(issuancesInDto.getReturnDate());
        issuances.setStatus(issuancesInDto.getStatus());
        issuances.setIssuanceType(issuancesInDto.getIssuanceType());

        return issuances;
    }
}
