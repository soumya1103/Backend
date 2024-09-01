package com.libraryManagement.backend.mapper;

import com.libraryManagement.backend.dto.UsersInDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.entity.Users;

public class UsersMapper {

    public static UsersOutDto mapToUsersOutDto(Users users) {
        UsersOutDto usersOutDto = new UsersOutDto();
        usersOutDto.setUserId(users.getUserId());
        usersOutDto.setUserName(users.getUserName());
        usersOutDto.setUserCredential(users.getUserCredential());
        usersOutDto.setPassword(users.getPassword());
        usersOutDto.setRole(users.getRole());

        return usersOutDto;
    }

    public static Users mapToUsersEntity(UsersInDto usersInDto) {
        Users users = new Users();
        users.setUserId(usersInDto.getUserId());
        users.setUserName(usersInDto.getUserName());
        users.setUserCredential(usersInDto.getUserCredential());
        users.setPassword(usersInDto.getPassword());
        users.setRole(usersInDto.getRole());

        return users;
    }
}
