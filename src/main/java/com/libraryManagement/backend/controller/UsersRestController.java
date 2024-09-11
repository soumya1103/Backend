package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.dto.UsersInDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import com.libraryManagement.backend.dto.response.ApiResponse;
import com.libraryManagement.backend.exception.ResourceNotAllowedToDeleteException;
import com.libraryManagement.backend.exception.ResourceNotFoundException;
import com.libraryManagement.backend.service.iIssuancesService;
import com.libraryManagement.backend.service.iUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lms/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersRestController {

    private iUserService usersService;

    private iIssuancesService issuancesService;

    public UsersRestController(iUserService usersService, iIssuancesService issuancesService) {
        this.usersService = usersService;
        this.issuancesService = issuancesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsersOutDto>> getAllUsersByRole() {
        List<UsersOutDto> usersList = usersService.getAllUsersByRole("ROLE_USER");
        if (usersList.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("")
    public ResponseEntity<Page<UsersOutDto>> getUsersByRole(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "userId"));
        Page<UsersOutDto> usersPage = usersService.getUsersByRole(pageable, "ROLE_USER");
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
        Optional<UsersOutDto> usersOutDto = usersService.findById(userId);
        if (usersOutDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND,"User not found."));
        }
        return ResponseEntity.ok(usersOutDto.get());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        return ResponseEntity.ok(usersService.getUserCount());
    }

    @GetMapping("/credential/{userCredential}")
    public ResponseEntity<?> getUserByUserCredential(@PathVariable String userCredential) {
        UsersOutDto usersOutDto = usersService.getUserByUserCredential(userCredential);
        if (usersOutDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND,"User not found with credentials."));
        }
        return ResponseEntity.ok(usersOutDto);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody UsersInDto usersInDto){
        UsersOutDto savedUser = usersService.registerUser(usersInDto);
        if (savedUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST,"User could not be registered"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED,"User registered successfully."));
    }

    @PutMapping("/id/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody UsersInDto usersInDto) {
        usersInDto.setUserId(userId);
        UsersOutDto updatedUser = usersService.updateUser(usersInDto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"User updated successfully."));

    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable int userId) {
        Optional<UsersOutDto> users = usersService.findById(userId);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        List<IssuancesOutDto> userIssuances = issuancesService.findByUserId(userId);
        boolean hasIssuedBooks = userIssuances.stream()
                .anyMatch(issuance -> "Issued".equalsIgnoreCase(issuance.getStatus()));

        if (hasIssuedBooks) {
            throw new ResourceNotAllowedToDeleteException("User cannot be deleted because they have issued books.");
        }

        usersService.deleteById(userId);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"User deleted successfully."));
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<?> searchUserByUserCredential(@PathVariable String keywords) {
        List<UsersOutDto> usersOutDto = usersService.searchByUserCredential(keywords);
        if (usersOutDto.isEmpty()) {
            throw new ResourceNotFoundException("No data found.");
        }
        return ResponseEntity.ok(usersOutDto);
    }
}
