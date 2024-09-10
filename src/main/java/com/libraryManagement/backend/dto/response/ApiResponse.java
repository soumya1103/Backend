package com.libraryManagement.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String status;
    private String message;

    public ApiResponse(HttpStatus httpStatus, String message) {
        status = String.valueOf(httpStatus);
        this.message = message;
    }
}
