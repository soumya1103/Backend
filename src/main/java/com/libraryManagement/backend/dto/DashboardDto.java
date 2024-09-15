package com.libraryManagement.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDto {
    private Long categoryCount;
    private Long userCount;
    private Long issuanceCountByType;
}
