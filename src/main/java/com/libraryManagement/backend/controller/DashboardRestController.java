package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.DashboardDto;
import com.libraryManagement.backend.service.iCategoriesService;
import com.libraryManagement.backend.service.iIssuancesService;
import com.libraryManagement.backend.service.iUserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping(path="/lms/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardRestController {

    @Autowired
    private iCategoriesService categoriesService;

    @Autowired
    private iUserService usersService;

    @Autowired
    private iIssuancesService issuancesService;

    @GetMapping("/count/all")
    public ResponseEntity<DashboardDto> getAllCounts() {
        Long categoryCount = categoriesService.getCategoryCount();
        Long userCount = usersService.getUserCount();
        Long issuanceCountByType = issuancesService.getIssuanceCountByType();

        DashboardDto dashboardCounts = new DashboardDto(categoryCount, userCount, issuanceCountByType);

        return ResponseEntity.status(HttpStatus.OK).body(dashboardCounts);
    }
}
