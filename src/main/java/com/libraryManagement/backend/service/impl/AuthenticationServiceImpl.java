package com.libraryManagement.backend.service.impl;


import com.libraryManagement.backend.constants.JwtConstants;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.dto.auth.LoginDto;
import com.libraryManagement.backend.service.iAuthenticationService;
import com.libraryManagement.backend.service.iUserService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements iAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final iUserService iUserService;

    @Override
    public UsersOutDto login(LoginDto loginRequestDTO) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.getUserCredential(),
                loginRequestDTO.getPassword());

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
                        .expiration(new java.util.Date((new java.util.Date()).getTime() + 30000000))
                        .signWith(secretKey).compact();
            }
        }

        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authenticationResponse.getAuthorities();
        List<String> roles = new ArrayList<>();
        grantedAuthorities.forEach(grantedAuthority -> roles.add(grantedAuthority.toString()));

        String username = loginRequestDTO.getUserCredential();

        UsersOutDto usersOutDto = new UsersOutDto();

        usersOutDto = iUserService.getUserByUserCredential(username);

        usersOutDto.setToken(jwt);
        usersOutDto.setRole(roles.get(0));

        return usersOutDto;
    }
}