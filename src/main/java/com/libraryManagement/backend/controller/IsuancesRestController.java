package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.service.iIssuancesService;
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
    public List<IssuancesOutDto> findAll() {
        return issuancesService.findAll();
    }

    @GetMapping("/issuances/id/{issuanceId}")
    public ResponseEntity<IssuancesOutDto> getIssuances(@PathVariable int issuanceId) {
        IssuancesOutDto issuancesOutDto = issuancesService.findById(issuanceId);

        return ResponseEntity.ok(issuancesOutDto);
    }

    @GetMapping("/issuances/type")
    public List<IssuancesOutDto> findByIssuanceType() {
        return issuancesService.getIssuanceByIssuanceType("Inhouse");
    }

    @GetMapping("/issuances/type/count")
    public ResponseEntity<Long> getIssaunceByTypeCount() {
        return ResponseEntity.ok(issuancesService.getIssuanceCountByType());
    }

    @GetMapping("/issuance/user/{userCredential}")
    public ResponseEntity<List<IssuancesOutDto>> getByUserCredential(@PathVariable Users userCredential) {
        List<IssuancesOutDto> issuances = issuancesService.getIssuanceByUserCredential(String.valueOf(userCredential));

        if (issuances.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(issuances);
        }
    }
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

    @PostMapping("/issuances")
    public ResponseEntity<IssuancesOutDto> addIssuance(@RequestBody IssuancesInDto issuancesInDto) {
        IssuancesOutDto issuancesOutDto = issuancesService.saveIssuances(issuancesInDto);

        return ResponseEntity.ok(issuancesOutDto);
    }

    @PutMapping("/issuances/id/{issuanceId}")
    public ResponseEntity<IssuancesOutDto> updateIssuance(@PathVariable int issuanceId, @RequestBody IssuancesInDto issuancesInDto) {
        IssuancesOutDto updatedIssuance = issuancesService.updateIssuance(issuanceId, issuancesInDto);
        return ResponseEntity.ok(updatedIssuance);
    }

//    @DeleteMapping("/issuances/id/{issuanceId}")
//    public String removeIssuance(@PathVariable int issuanceId) {
//
//        IssuancesOutDto issuancesOutDto = issuancesService.findById(issuanceId);
//
//        issuancesService.deleteById(issuanceId);
//
//        return "Deleted issuance id: " + issuanceId;
//    }

}
