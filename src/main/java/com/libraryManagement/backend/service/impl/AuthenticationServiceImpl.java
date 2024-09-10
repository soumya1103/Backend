package com.libraryManagement.backend.service.impl;


import com.libraryManagement.backend.constants.JwtConstants;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.dto.auth.LoginInDto;
import com.libraryManagement.backend.dto.auth.LoginOutDto;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.mapper.UsersMapper;
import com.libraryManagement.backend.repository.UsersRepository;
import com.libraryManagement.backend.service.iAuthenticationService;
import com.libraryManagement.backend.service.iUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements iAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final iUserService iUserService;
    private final UsersRepository usersRepository;

    @Override
    public LoginOutDto login(LoginInDto loginInDto) {
        System.out.println("Login service called");
        String encodedPassword = loginInDto.getPassword();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        String decodedPassword = new String(decodedBytes);
        loginInDto.setPassword(decodedPassword);

        System.out.println(loginInDto);

        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginInDto.getUserCredential(),
                loginInDto.getPassword());

        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if(null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != env) {
                String secret = env.getProperty(JwtConstants.JWT_SECRET_KEY,
                        JwtConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder().issuer("Lms").subject("JWT Token")
                        .claim("userCredential", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new java.util.Date())
                        .expiration(new java.util.Date((new java.util.Date()).getTime() + 20000000))
                        .signWith(secretKey).compact();
            }
        }

        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authenticationResponse.getAuthorities();
        List<String> roles = new ArrayList<>();
        grantedAuthorities.forEach(grantedAuthority -> roles.add(grantedAuthority.toString()));

        String userCredential = loginInDto.getUserCredential();

        UsersOutDto usersOutDto = new UsersOutDto();

        usersOutDto.setRole(roles.get(0));

        LoginOutDto loginOutDto = new LoginOutDto();

//        loginOutDto.setUserCredential(usersOutDto.getUserCredential());
        loginOutDto.setUserCredential(loginInDto.getUserCredential());

        loginOutDto.setRole(usersOutDto.getRole());
        loginOutDto.setToken(jwt);

        return loginOutDto;
    }

    @Override
    public LoginOutDto getUserByToken(String jwt) {
        String secret = env.getProperty(JwtConstants.JWT_SECRET_KEY, JwtConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(jwt).getPayload();
        String userCredential = String.valueOf(claims.get("userCredential"));

        Users users = usersRepository.findByUserCredential(userCredential);

        if (users == null) {
            throw new RuntimeException("Invalid token");
        }

            UsersOutDto usersOutDto = UsersMapper.mapToUsersOutDto(users);
            LoginOutDto loginOutDto = new LoginOutDto();

            loginOutDto.setUserCredential(usersOutDto.getUserCredential());
            loginOutDto.setRole(usersOutDto.getRole());
            loginOutDto.setToken(jwt);
            return  loginOutDto;
        }
}