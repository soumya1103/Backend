package com.libraryManagement.backend.dto.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LoginOutDto {

    private String userCredential;

    private String role;

    private String token;

}
