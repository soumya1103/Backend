package com.libraryManagement.backend.dto.auth;

import lombok.Data;

@Data
public class LoginDto {

    private String userCredential;

    private String password;

}