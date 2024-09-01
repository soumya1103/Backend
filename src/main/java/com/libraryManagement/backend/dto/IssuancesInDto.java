package com.libraryManagement.backend.dto;

import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Users;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssuancesInDto {

    private int issuanceId;

    private Integer userId;

    private Integer bookId;

    private LocalDateTime issueDate;

    private LocalDateTime returnDate;

    private String status;

    private String issuanceType;

}
