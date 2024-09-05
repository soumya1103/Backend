package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.constants.JwtConstants;
import com.libraryManagement.backend.dto.auth.LoginInDto;
import com.libraryManagement.backend.dto.auth.LoginOutDto;
import com.libraryManagement.backend.dto.response.ResponseDto;
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
    public ResponseEntity<LoginOutDto> login(@RequestBody LoginInDto loginInDto, HttpServletResponse response) {
        LoginOutDto loginOutDto = iAuthenticationService.login(loginInDto);

        return ResponseEntity.status(HttpStatus.OK).header(JwtConstants.JWT_HEADER,loginOutDto.getToken())
                .body(loginOutDto);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> currentUser(@RequestHeader("Authorization") String token) {
        LoginOutDto loginOutDto = iAuthenticationService.getUserByToken(token);

        if (loginOutDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(loginOutDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(HttpStatus.UNAUTHORIZED.toString(), "Invalid token"));
        }
    }
}
