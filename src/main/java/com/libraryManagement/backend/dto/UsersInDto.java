package com.libraryManagement.backend.dto;

import lombok.Data;

@Data
public class UsersInDto {

    private int userId;

    private String userName;

    private String userCredential;

    private String password;

    private String role;

}
