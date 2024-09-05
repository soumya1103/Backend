package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.constants.JwtConstants;
import com.libraryManagement.backend.dto.UsersInDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.mapper.UsersMapper;
import com.libraryManagement.backend.repository.UsersRepository;
import com.libraryManagement.backend.service.iUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements iUserService {

    @Autowired
    private UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final Environment env;

    @Override
    public Page<UsersOutDto> getUsersByRole(Pageable pageable, String role) {
        Page<Users> usersPage;
        usersPage = usersRepository.findByRole(pageable, role);

        return usersPage.map(UsersMapper::mapToUsersOutDto);
    }

    @Override
    public Optional<UsersOutDto> findById(int userId) {
        System.out.println("User Id" + userId);
        Optional<UsersOutDto> usersOutDto = Optional.ofNullable(usersRepository.findById(userId)
                .filter(users -> "ROLE_USER".equals(users.getRole()))
                .map(UsersMapper::mapToUsersOutDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)));

        return usersOutDto;

    }

    @Override
    public Users save(Users users) {
        System.out.println("Users" + users.toString());
        String randomPassword = generateRandomPassword();
        users.setRole("ROLE_USER");

        // Encrypt the generated password
        String encryptedPassword = generatePassword(randomPassword);
        users.setPassword(encryptedPassword);

        return usersRepository.save(users);
    }

    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }


    public String generatePassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public UsersOutDto updateUser(UsersInDto usersInDto) {
        Optional<Users> optionalUsers = usersRepository.findById(usersInDto.getUserId());

        if(!optionalUsers.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Users userToUpdate = optionalUsers.get();

        if (!"ROLE_USER".equals(userToUpdate.getRole())) {
            throw new RuntimeException("Only users with role USER can be updated");
        }

        if (usersInDto.getUserName() != null && !usersInDto.getUserName().isEmpty()) {
            userToUpdate.setUserName(usersInDto.getUserName());
        }

        if (usersInDto.getUserCredential() != null && !usersInDto.getUserCredential().isEmpty()) {
            userToUpdate.setUserCredential(usersInDto.getUserCredential());
        }

        if (usersInDto.getPassword() != null && !usersInDto.getPassword().isEmpty()) {
            userToUpdate.setPassword(usersInDto.getPassword());
        }

        Users updatedUser = usersRepository.save(userToUpdate);

        UsersOutDto usersOutDto = new UsersOutDto();
        usersOutDto.setUserId(updatedUser.getUserId());
        usersOutDto.setUserName(updatedUser.getUserName());
        usersOutDto.setUserCredential(updatedUser.getUserCredential());

        return usersOutDto;
    }

    @Override
    public void deleteById(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public long getUserCount() {
        return usersRepository.count();
    }

    @Override
    public UsersOutDto getUserByUserCredential(String userCredential) {
        Users users =  usersRepository.findByUserCredential(userCredential);

        return UsersMapper.mapToUsersOutDto(users);
    }

    @Override
    public UsersOutDto getUserByToken(String token) {
        String secret = env.getProperty(JwtConstants.JWT_SECRET_KEY, JwtConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload();
        String username = String.valueOf(claims.get("userCredential"));

            Users users = usersRepository.findByUserCredential(username);

            UsersOutDto usersOutDto  = UsersMapper.mapToUsersOutDto(users);
            usersOutDto.setToken(token);
            return  usersOutDto;
    }

    @Override
    public List<UsersOutDto> getAllUsersByRole(String roleUser) {
        List<UsersOutDto> usersOutDto = usersRepository.findUserByRole(roleUser)
                .stream().map(UsersMapper::mapToUsersOutDto).toList();

        return usersOutDto;
    }

    @Override
    public List<UsersOutDto> searchByUserCredential(String keywords) {
        List<Users> users = usersRepository.findByUserCredentialOrUserNameContaining("%" + keywords + "%");
        return users.stream().map(UsersMapper::mapToUsersOutDto).toList();
    }
}
