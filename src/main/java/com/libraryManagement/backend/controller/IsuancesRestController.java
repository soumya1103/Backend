package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.BooksOutDto;
import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.dto.UsersOutDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.libraryManagement.backend.service.iIssuancesService;

import java.util.List;

@RestController
@RequestMapping("/lms/issuances")
@CrossOrigin(origins = "http://localhost:3000")
public class IsuancesRestController {

    private iIssuancesService issuancesService;

    public IsuancesRestController(iIssuancesService issuancesService) {
        this.issuancesService = issuancesService;
    }

    @GetMapping("")
    public List<IssuancesOutDto> findAll() {
        return issuancesService.findAll();
    }

    @GetMapping("/id/{issuanceId}")
    public ResponseEntity<IssuancesOutDto> getIssuances(@PathVariable int issuanceId) {
        IssuancesOutDto issuancesOutDto = issuancesService.findById(issuanceId);

        return ResponseEntity.ok(issuancesOutDto);
    }

    @GetMapping("/type")
    public List<IssuancesOutDto> findByIssuanceType() {
        return issuancesService.getIssuanceByIssuanceType("Inhouse");
    }

    @GetMapping("/type/count")
    public ResponseEntity<Long> getIssaunceByTypeCount() {
        return ResponseEntity.ok(issuancesService.getIssuanceCountByType());
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<IssuancesOutDto>> findByUserId(@PathVariable Users userId) {
//        List<IssuancesOutDto> issuances = issuancesService.findByUserId(userId);
//
//        if (issuances.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(issuances);
//        }
//    }
//
//    @GetMapping("/issuances/book/{bookId}")
//    public ResponseEntity<List<IssuancesOutDto>> findByBookId(@PathVariable Books bookId) {
//        List<IssuancesOutDto> issuances = issuancesService.findByBookId(bookId);
//
//        if (issuances.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(issuances);
//        }
//    }
//

    @PostMapping("")
    public ResponseEntity<IssuancesOutDto> addIssuance(@RequestBody IssuancesInDto issuancesInDto) {
        IssuancesOutDto issuancesOutDto = issuancesService.saveIssuances(issuancesInDto);

        return ResponseEntity.ok(issuancesOutDto);
    }

    @PutMapping("/id/{issuanceId}")
    public ResponseEntity<IssuancesOutDto> updateIssuance(@PathVariable int issuanceId, @RequestBody IssuancesInDto issuancesInDto) {
        IssuancesOutDto updatedIssuance = issuancesService.updateIssuance(issuanceId, issuancesInDto);
        return ResponseEntity.ok(updatedIssuance);
    }

    @DeleteMapping("/id/{issuanceId}")
    public String removeIssuance(@PathVariable int issuanceId) {

        IssuancesOutDto issuancesOutDto = issuancesService.findById(issuanceId);

        issuancesService.deleteById(issuanceId);

        return "Deleted issuance id: " + issuanceId;
    }

//    @DeleteMapping("/issuances/{issuanceId}")
//    public String removeIssuance(@PathVariable int issuanceId) {
//
//        Optional<IssuancesOutDto> issuancesOutDto = issuancesService.findById(issuanceId);
//        if (issuancesOutDto.isEmpty()) {
//            throw new RuntimeException("Issuance not found: " + issuanceId);
//        }
//        issuancesService.deleteById(issuanceId);
//
//        return "Deleted issuance id: " + issuanceId;
//
//    }
}
