package com.libraryManagement.backend.dto.auth;

import lombok.Data;

@Data
public class LoginDto {

    //    It will be either mobileNumber or email
    private String userCredential;

    private String password;

}