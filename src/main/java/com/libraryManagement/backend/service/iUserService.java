package com.libraryManagement.backend.service;


import com.libraryManagement.backend.dto.UsersInDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.entity.Users;

import java.util.List;
import java.util.Optional;

public interface iUserService {

    List<UsersOutDto> getUsersByRole(String role);

    Optional<UsersOutDto> findById(int theId);

    Users save(Users users);

    UsersOutDto updateUser(UsersInDto usersInDto);

    void deleteById(int id);

    long getUserCount();

    UsersOutDto getUserByUserCredential(String userCredential);

    UsersOutDto getUserByToken(String token);

}
