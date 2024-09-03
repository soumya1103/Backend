package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.dto.auth.LoginDto;
import org.springframework.stereotype.Service;

@Service
public interface iAuthenticationService {

    UsersOutDto login(LoginDto loginRequestDTO);

}