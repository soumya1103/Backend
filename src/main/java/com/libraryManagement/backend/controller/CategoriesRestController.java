package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.CategoriesInDto;
import com.libraryManagement.backend.dto.CategoriesOutDto;
import com.libraryManagement.backend.entity.Categories;
import com.libraryManagement.backend.service.iCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lms/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriesRestController {

    @Autowired
    private iCategoriesService categoriesService;

    @GetMapping("")
    public List<CategoriesOutDto> findAll() {
        return categoriesService.findAll();
    }

    @GetMapping("/id/{categoryId}")
    public Optional<CategoriesOutDto> getCategory(@PathVariable int categoryId) {
        Optional<CategoriesOutDto> categoriesOutDto = categoriesService.findById(categoryId);

        return categoriesOutDto;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCategoryCount() {
        return ResponseEntity.ok(categoriesService.getCategoryCount());
    }

    @GetMapping("/name/{categoryName}")
    public Categories getCategoryByName(@PathVariable String categoryName) {
        Categories categoriesOutDto = categoriesService.findByCategoryNameIgnoreCase(categoryName);

        return categoriesOutDto;
    }

    @PostMapping("")
    public Categories addCategory(@RequestBody Categories categories) {
        Categories dbCategory = categoriesService.save(categories);
        return dbCategory;
    }

    @PutMapping("/id/{categoryId}")
    public ResponseEntity<CategoriesOutDto> updateCategory(@PathVariable int categoryId, @RequestBody CategoriesInDto categoriesInDto) {
        categoriesInDto.setCategoryId(categoryId);
        CategoriesOutDto updatedCategory = categoriesService.updateCategory(categoriesInDto);

        return ResponseEntity.ok(updatedCategory);
    }

    @PutMapping("/name/{categoryName}")
    public ResponseEntity<CategoriesOutDto> updateCategoryByName(
            @PathVariable String categoryName,
            @RequestBody CategoriesInDto categoriesInDto) {
        CategoriesOutDto updatedCategory = categoriesService.updateCategoryByName(categoryName, categoriesInDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/id/{categoryId}")
    public String removeCategory(@PathVariable int categoryId) {

        Optional<CategoriesOutDto> categoriesOutDto = categoriesService.findById(categoryId);
        if (categoriesOutDto.isEmpty()) {
            throw new RuntimeException("Category not found: " + categoryId);
        }
        categoriesService.deleteById(categoryId);

        return "Deleted category successfully";
    }

    @DeleteMapping("/name/{categoryName}")
    public String deleteByCategoryName(@PathVariable String categoryName) {
        Categories categories = categoriesService.findByCategoryNameIgnoreCase(categoryName);
        if (categories == null) {
            throw new RuntimeException("Category not found: " + categoryName);
        }
        categoriesService.deleteById(categories.getCategoryId());

        return "Deleted category successfully";
    }

}
