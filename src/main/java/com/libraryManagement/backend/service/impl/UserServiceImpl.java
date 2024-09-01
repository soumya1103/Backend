package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.UsersInDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.mapper.IssuancesMapper;
import com.libraryManagement.backend.mapper.UsersMapper;
import com.libraryManagement.backend.repository.UsersRepository;
import com.libraryManagement.backend.service.iUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements iUserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<UsersOutDto> getUsersByRole(String role) {
        List<Users> usersList = usersRepository.findByRole(role);
        List<UsersOutDto> usersOutDtoList = new ArrayList<>();

        usersList.forEach(users -> usersOutDtoList.add(UsersMapper.mapToUsersOutDto(users)));

        return usersOutDtoList;
    }

    @Override
    public Optional<UsersOutDto> findById(int userId) {
        Optional<UsersOutDto> usersOutDto = Optional.ofNullable(usersRepository.findById(userId)
                .filter(users -> "USER".equals(users.getRole()))
                .map(UsersMapper::mapToUsersOutDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)));

        return usersOutDto;

    }

    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public UsersOutDto updateUser(UsersInDto usersInDto) {
        Optional<Users> optionalUsers = usersRepository.findById(usersInDto.getUserId());

        if(!optionalUsers.isPresent()) {
            throw new RuntimeException("User not found");
        }

        Users userToUpdate = optionalUsers.get();

        if (!"USER".equals(userToUpdate.getRole())) {
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
}
