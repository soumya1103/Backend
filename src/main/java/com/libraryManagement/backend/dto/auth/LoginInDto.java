package com.libraryManagement.backend.dto.auth;

import lombok.Data;

@Data
public class LoginInDto {

    private String userCredential;

    private String password;

}