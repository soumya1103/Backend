package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.constants.JwtConstants;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.dto.auth.LoginDto;
import com.libraryManagement.backend.service.iAuthenticationService;
import com.libraryManagement.backend.service.iUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/lms", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final iUserService iUserService;
    private final iAuthenticationService iAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<UsersOutDto> login(@RequestBody LoginDto loginRequestDTO, HttpServletResponse response) {

        UsersOutDto userDTO = iAuthenticationService.login(loginRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).header(JwtConstants.JWT_HEADER,userDTO.getToken())
                .body(userDTO);

    }


    @GetMapping("/current-user")
    public ResponseEntity<UsersOutDto> currentUser(@RequestHeader("Authorization") String token) {
        UsersOutDto user = iUserService.getUserByToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(user);

//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
    }

}