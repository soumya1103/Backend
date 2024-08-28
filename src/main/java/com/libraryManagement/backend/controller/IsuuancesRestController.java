package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.libraryManagement.backend.service.iIssuancesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lms")
@CrossOrigin(origins = "http://localhost:3000")
public class IsuuancesRestController {

    private iIssuancesService issuancesService;

    public IsuuancesRestController(iIssuancesService issuancesService) {
        this.issuancesService = issuancesService;
    }

    @GetMapping("/issuances")
    public List<IssuancesOutDto> findAll() {
        return issuancesService.findAll();
    }

    @GetMapping("/issuances/{issuanceId}")
    public Optional<IssuancesOutDto> getIssuances(@PathVariable int issuanceId) {
        Optional<IssuancesOutDto> issuancesOutDto = issuancesService.findById(issuanceId);

        return issuancesOutDto;
    }

    @GetMapping("/issuances/user/{userId}")
    public ResponseEntity<List<IssuancesOutDto>> findByUserId(@PathVariable Users userId) {
        List<IssuancesOutDto> issuances = issuancesService.findByUserId(userId);

        if (issuances.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(issuances);
        }
    }

    @GetMapping("/issuances/book/{bookId}")
    public ResponseEntity<List<IssuancesOutDto>> findByBookId(@PathVariable Books bookId) {
        List<IssuancesOutDto> issuances = issuancesService.findByBookId(bookId);

        if (issuances.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(issuances);
        }
    }

    @PostMapping("/issuances")
    public Issuances addIssuance(@RequestBody Issuances issuances) {
        Issuances dbIssuance = issuancesService.save(issuances);

        return dbIssuance;
    }

    @PutMapping("/issuances/{issuanceId}")
    public ResponseEntity<IssuancesOutDto> updateIssuance(@PathVariable int issuanceId, @RequestBody IssuancesInDto issuancesInDto) {
        issuancesInDto.setIssuanceId(issuanceId); // Set the path variable to DTO
        IssuancesOutDto updatedIssuance = issuancesService.updateIssuance(issuancesInDto);
        return ResponseEntity.ok(updatedIssuance);
    }

    @DeleteMapping("/issuances/{issuanceId}")
    public String removeIssuance(@PathVariable int issuanceId) {

        Optional<IssuancesOutDto> issuancesOutDto = issuancesService.findById(issuanceId);
        if (issuancesOutDto.isEmpty()) {
            throw new RuntimeException("Issuance not found: " + issuanceId);
        }
        issuancesService.deleteById(issuanceId);

        return "Deleted issuance id: " + issuanceId;

    }
}
