package com.libraryManagement.backend.dto.response;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @RequiredArgsConstructor
public class ResponseDto {

    private String status;

    private String message;

}