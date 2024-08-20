package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.UsersInDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.service.iUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lms")
public class UsersRestController {

    private iUserService usersService;

    public UsersRestController(iUserService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/user")
    public List<UsersOutDto> findUsersByRole() {
        return usersService.getUsersByRole("USER");
    }

    @GetMapping("/user/{userId}")
    public Optional<UsersOutDto> getUser(@PathVariable int userId) {
        Optional<UsersOutDto> usersOutDto = usersService.findById(userId);

        return usersOutDto;
    }


    @PostMapping("/admin")
    public Users addAdmin(@RequestBody Users users){
        users.setRole("ADMIN");
        Users dbAdmin = usersService.save(users);

        return  dbAdmin;
    }

    @PostMapping("/user")
    public Users addUser(@RequestBody Users users){
        users.setRole("USER");
        Users dbUser = usersService.save(users);

        return  dbUser;
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UsersOutDto> updateUser(@PathVariable int userId, @RequestBody UsersInDto usersInDto) {
        usersInDto.setUserId(userId);
        UsersOutDto updatedUser = usersService.updateUser(usersInDto);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{userId}")
    public String removeUser(@PathVariable int userId) {
        Optional<UsersOutDto> users = usersService.findById(userId);
        if (users.isEmpty()) {
            throw new RuntimeException("Employee id not found: " + userId);
        }
        usersService.deleteById(userId);
        return "Deleted employee id: " + userId;
    }


}
