package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.dto.response.ApiResponse;
import com.libraryManagement.backend.service.iIssuancesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lms")
@CrossOrigin(origins = "http://localhost:3000")
public class IsuancesRestController {

    private iIssuancesService issuancesService;

    public IsuancesRestController(iIssuancesService issuancesService) {
        this.issuancesService = issuancesService;
    }

    @GetMapping("/issuances")
    public ResponseEntity<?> getIssuances(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        try {
            Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "issuanceId"));
            Page<IssuancesOutDto> issuances = issuancesService.getIssuances(pageable);
            return ResponseEntity.ok(issuances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/issuances/id/{issuanceId}")
    public ResponseEntity<?> getIssuances(@PathVariable int issuanceId) {
        try {
            IssuancesOutDto issuancesOutDto = issuancesService.findById(issuanceId);
            if (issuancesOutDto != null) {
                return ResponseEntity.ok(issuancesOutDto);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(HttpStatus.CONFLICT,"Issuance not found."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error retrieving issuance."));
        }
    }

    @GetMapping("/issuances/type")
    public ResponseEntity<?> findByIssuanceType() {
        try {
            List<IssuancesOutDto> issuances = issuancesService.getIssuanceByIssuanceType("Inhouse");
            return ResponseEntity.ok(issuances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error retrieving issuance by type."));
        }
    }

    @GetMapping("/issuances/type/count")
    public ResponseEntity<?> getIssuanceByTypeCount() {
        try {
            Long count = issuancesService.getIssuanceCountByType();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error retrieving issuance count."));
        }
    }

    @GetMapping("/issuance/user/{userCredential}")
    public ResponseEntity<?> getByUserCredential(@PathVariable String userCredential) {
        try {
            List<IssuancesOutDto> issuances = issuancesService.getIssuanceByUserCredential(userCredential);
            if (issuances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND,"No issuances found."));
            }
            return ResponseEntity.ok(issuances);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving issuances for user.");
        }
    }

    @GetMapping("/issuances/book/{bookId}")
    public ResponseEntity<?> findByBookId(@PathVariable int bookId) {
        try {
            List<IssuancesOutDto> issuances = issuancesService.findByBookId(bookId);
            if (issuances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND, "No issuances found."));
            }
            return ResponseEntity.ok(issuances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error retrieving issuances for book."));
        }
    }

    @GetMapping("/issuances/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable int userId) {
        try {
            List<IssuancesOutDto> issuances = issuancesService.findByUserId(userId);
            if (issuances.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND,"No issuances found."));
            }
            return ResponseEntity.ok(issuances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error retrieving issuances for user."));
        }
    }

    @PostMapping("/issuances")
    public ResponseEntity<?> addIssuance(@RequestBody IssuancesInDto issuancesInDto) {
        try {
            IssuancesOutDto issuancesOutDto = issuancesService.saveIssuances(issuancesInDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED, "Issuance created successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error creating issuance"));
        }
    }

    @PutMapping("/issuances/id/{issuanceId}")
    public ResponseEntity<?> updateIssuance(@PathVariable int issuanceId, @RequestBody IssuancesInDto issuancesInDto) {
        try {
            IssuancesOutDto updatedIssuance = issuancesService.updateIssuance(issuanceId, issuancesInDto);
            return ResponseEntity.ok().body(new ApiResponse(HttpStatus.OK, "Issuance updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error updating issuance."));
        }

    }

    @GetMapping("/issuances/search/{keywords}")
    public ResponseEntity<?> searchPostByUserCredential(@PathVariable String keywords) {
        try {
            List<IssuancesOutDto> issuancesOutDto = issuancesService.searchCredential(keywords);
            return ResponseEntity.ok(issuancesOutDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST,"Error searching issuances."));
        }
    }

}
