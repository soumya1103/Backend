package com.libraryManagement.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssuancesOutDto {

    private LocalDateTime issueDate;

    private LocalDateTime returnDate;

    private String status;

    private String issuanceType;
}
