package com.libraryManagement.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssuancesOutDto {

    private int issuanceId;

    private String userCredential;

    private String userName;

    private String bookTitle;

    private LocalDateTime issueDate;

    private LocalDateTime returnDate;

    private String status;

    private String issuanceType;

    private int userId;

    private int bookId;
}
