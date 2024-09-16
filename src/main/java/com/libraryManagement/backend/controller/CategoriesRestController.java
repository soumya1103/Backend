package com.libraryManagement.backend.controller;

import com.libraryManagement.backend.dto.CategoriesInDto;
import com.libraryManagement.backend.dto.CategoriesOutDto;
import com.libraryManagement.backend.dto.response.ApiResponse;
import com.libraryManagement.backend.entity.Categories;
import com.libraryManagement.backend.exception.ResourceNotFoundException;
import com.libraryManagement.backend.service.iCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/lms/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriesRestController {

    @Autowired
    private iCategoriesService categoriesService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoriesOutDto>> getAllCategories() {
        List<CategoriesOutDto> categories = categoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("")
    public ResponseEntity<Page<CategoriesOutDto>> getCategories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
            ){
        if (page == null || size == null) {
            return ResponseEntity.badRequest().body(null);
        }
            Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Direction.DESC, "categoryId"));
        Page<CategoriesOutDto> categoriesPage = categoriesService.getCategories(pageable);
        return ResponseEntity.ok(categoriesPage);
    }

    @GetMapping("/id/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable int categoryId) {
        Optional<CategoriesOutDto> categoriesOutDto = Optional.ofNullable(categoriesService.findById(categoryId));
        if (categoriesOutDto.isPresent()) {
            return ResponseEntity.ok(categoriesOutDto.get());
        } else {
            throw new ResourceNotFoundException("Category not found.");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCategoryCount() {
        return ResponseEntity.ok(categoriesService.getCategoryCount());
    }

    @GetMapping("/name/{categoryName}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String categoryName) {
        Categories category = categoriesService.findByCategoryNameIgnoreCase(categoryName);
        if (category != null) {
            return ResponseEntity.status(HttpStatus.OK).body(category);
        } else {
            throw new ResourceNotFoundException("Category not found.");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addCategory(@RequestBody Categories categories) {
        Categories dbCategory = categoriesService.save(categories);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.CREATED, "Category saved successfully."));
    }

    @PutMapping("/id/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable int categoryId, @RequestBody CategoriesInDto categoriesInDto) {
            categoriesInDto.setCategoryId(categoryId);
            CategoriesOutDto updatedCategory = categoriesService.updateCategory(categoriesInDto);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"Category updated successfully."));
    }

    @DeleteMapping("/id/{categoryId}")
    public ResponseEntity<?> removeCategory(@PathVariable int categoryId) {
            categoriesService.deleteById(categoryId);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK,"Category deleted successfully."));
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<CategoriesOutDto>> searchByCategoryName(@PathVariable String keywords) {
        List<CategoriesOutDto> categoriesOutDto = categoriesService.searchCategories(keywords);
        return ResponseEntity.ok(categoriesOutDto);
    }

}
