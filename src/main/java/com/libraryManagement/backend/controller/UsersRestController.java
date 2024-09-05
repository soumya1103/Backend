package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.UsersInDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.service.iUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lms/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersRestController {

    private iUserService usersService;

    public UsersRestController(iUserService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/all")
    public List<UsersOutDto> getAllUsersByRole() {
        return usersService.getAllUsersByRole("ROLE_USER");
    }

    @GetMapping("")
    public Page<UsersOutDto> getUsersByRole(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "userId"));
        return usersService.getUsersByRole(pageable, "ROLE_USER");
    }

    @GetMapping("/{userId}")
    public Optional<UsersOutDto> getUser(@PathVariable int userId) {
        Optional<UsersOutDto> usersOutDto = usersService.findById(userId);

        return usersOutDto;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok(usersService.getUserCount());
    }

    @GetMapping("/credential/{userCredential}")
    public ResponseEntity<UsersOutDto> getUserByUserCredential(@PathVariable String userCredential) {
        UsersOutDto usersOutDto = usersService.getUserByUserCredential(userCredential);
        return ResponseEntity.ok(usersOutDto);
    }

    @PostMapping("/admin")
    public Users addAdmin(@RequestBody Users users){
        users.setRole("ROLE_ADMIN");
        Users dbAdmin = usersService.save(users);

        return  dbAdmin;
    }

    @PostMapping("/user")
    public Users addUser(@RequestBody Users users){
        Users dbUser = usersService.save(users);

        return  dbUser;
    }

    @PutMapping("/id/{userId}")
    public ResponseEntity<UsersOutDto> updateUser(@PathVariable int userId, @RequestBody UsersInDto usersInDto) {
        usersInDto.setUserId(userId);
        UsersOutDto updatedUser = usersService.updateUser(usersInDto);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/id/{userId}")
    public String removeUser(@PathVariable int userId) {
        Optional<UsersOutDto> users = usersService.findById(userId);
        if (users.isEmpty()) {
            throw new RuntimeException("Employee id not found: " + userId);
        }
        usersService.deleteById(userId);
        return "Deleted employee id: " + userId;
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UsersOutDto>> searchUserByUserCredential(@PathVariable String keywords) {
        List<UsersOutDto> usersOutDto = usersService.searchByUserCredential(keywords);
        return ResponseEntity.ok(usersOutDto);
    }
}
