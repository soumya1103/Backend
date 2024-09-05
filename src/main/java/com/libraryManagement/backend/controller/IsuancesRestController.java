package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.service.iIssuancesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<IssuancesOutDto> getIssuances(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "issuanceId"));
        return issuancesService.getIssuances(pageable);
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
    public ResponseEntity<Long> getIssuanceByTypeCount() {
        return ResponseEntity.ok(issuancesService.getIssuanceCountByType());
    }

    @GetMapping("/issuance/user/{userCredential}")
    public ResponseEntity<List<IssuancesOutDto>> getByUserCredential(@PathVariable String userCredential) {
        List<IssuancesOutDto> issuances = issuancesService.getIssuanceByUserCredential(userCredential);

        if (issuances.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(issuances);
        }
    }

    @GetMapping("/issuances/book/{bookId}")
    public ResponseEntity<List<IssuancesOutDto>> findByBookId(@PathVariable int bookId) {
        List<IssuancesOutDto> issuances = issuancesService.findByBookId(bookId);

        if (issuances.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(issuances);
        }
    }

    @GetMapping("/issuances/user/{userId}")
    public ResponseEntity<List<IssuancesOutDto>> findByUserId(@PathVariable int userId) {
        List<IssuancesOutDto> issuances = issuancesService.findByUserId(userId);

        if (issuances.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(issuances);
        }
    }

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

    @GetMapping("/issuances/search/{keywords}")
    public ResponseEntity<List<IssuancesOutDto>> searchPostByUserCredential(@PathVariable String keywords) {
        List<IssuancesOutDto> issuancesOutDto = issuancesService.searchCredential(keywords);
        return ResponseEntity.ok(issuancesOutDto);
    }

}
