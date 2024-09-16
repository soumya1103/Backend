package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.dto.response.ApiResponse;
import com.libraryManagement.backend.exception.ResourceNotFoundException;
import com.libraryManagement.backend.service.iIssuancesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private iIssuancesService issuancesService;

    @GetMapping("/issuances")
    public ResponseEntity<?> getIssuances(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
            Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "issuanceId"));
            Page<IssuancesOutDto> issuances = issuancesService.getIssuances(pageable);
            return ResponseEntity.ok(issuances);
    }

    @GetMapping("/issuances/id/{issuanceId}")
    public ResponseEntity<?> getIssuances(@PathVariable int issuanceId) {
            IssuancesOutDto issuancesOutDto = issuancesService.findById(issuanceId);
            if (issuancesOutDto == null) {
                throw new ResourceNotFoundException("Issuance not found.");
            }
            return ResponseEntity.ok(issuancesOutDto);
    }

    @GetMapping("/issuances/type")
    public ResponseEntity<?> findByIssuanceType() {
            List<IssuancesOutDto> issuances = issuancesService.getIssuanceByIssuanceType("Inhouse");
            if (issuances.isEmpty()) {
                throw new ResourceNotFoundException("Issuance not found.");
            }
            return ResponseEntity.ok(issuances);
    }

    @GetMapping("/issuances/type/count")
    public ResponseEntity<?> getIssuanceByTypeCount() {
            Long count = issuancesService.getIssuanceCountByType();
            return ResponseEntity.ok(count);
    }

    @GetMapping("/issuance/user/{userCredential}")
    public ResponseEntity<?> getByUserCredential(@PathVariable String userCredential) {
            List<IssuancesOutDto> issuances = issuancesService.getIssuanceByUserCredential(userCredential);
            if (issuances.isEmpty()) {
                throw new ResourceNotFoundException("No issuances found.");
            }
            return ResponseEntity.ok(issuances);
    }

    @GetMapping("/issuances/book/{bookId}")
    public ResponseEntity<?> findByBookId(@PathVariable int bookId) {
            List<IssuancesOutDto> issuances = issuancesService.findByBookId(bookId);
            if (issuances.isEmpty()) {
                throw new ResourceNotFoundException("No issuances found.");
            }
            return ResponseEntity.ok(issuances);
    }

    @GetMapping("/issuances/user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable int userId) {
            List<IssuancesOutDto> issuances = issuancesService.findByUserId(userId);
            if (issuances.isEmpty()) {
                throw new ResourceNotFoundException("No issuances found.");
            }
            return ResponseEntity.ok(issuances);
    }

    @PostMapping("/issuances")
    public ResponseEntity<?> addIssuance(@RequestBody IssuancesInDto issuancesInDto) {
            IssuancesOutDto issuancesOutDto = issuancesService.saveIssuances(issuancesInDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED, "Issuance created successfully."));
    }

    @PutMapping("/issuances/id/{issuanceId}")
    public ResponseEntity<?> updateIssuance(@PathVariable int issuanceId, @RequestBody IssuancesInDto issuancesInDto) {
            IssuancesOutDto updatedIssuance = issuancesService.updateIssuance(issuanceId, issuancesInDto);
            return ResponseEntity.ok().body(new ApiResponse(HttpStatus.OK, "Issuance updated successfully."));
    }

    @GetMapping("/issuances/search/{keywords}")
    public ResponseEntity<?> searchIssuances(@PathVariable String keywords) {
            List<IssuancesOutDto> issuancesOutDto = issuancesService.searchByCredential("%" + keywords + "%");
            return ResponseEntity.ok(issuancesOutDto);
    }

}
