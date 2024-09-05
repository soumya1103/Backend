package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.auth.LoginInDto;
import com.libraryManagement.backend.dto.auth.LoginOutDto;
import org.springframework.stereotype.Service;

@Service
public interface iAuthenticationService {

    LoginOutDto login(LoginInDto loginInDto);
    LoginOutDto getUserByToken(String token);

}