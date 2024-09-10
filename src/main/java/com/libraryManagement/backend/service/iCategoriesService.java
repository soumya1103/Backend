package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.CategoriesInDto;
import com.libraryManagement.backend.dto.CategoriesOutDto;
import com.libraryManagement.backend.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iCategoriesService {

    Page<CategoriesOutDto> getCategories(Pageable pageable);

    Categories findByCategoryNameIgnoreCase(String categoryName);

    CategoriesOutDto findById(int categoryId);

    Categories save(Categories categories);

    CategoriesOutDto updateCategory(CategoriesInDto categoriesInDto);

    void deleteById(int categoryId);

    long getCategoryCount();

    List<CategoriesOutDto> getAllCategories();

    List<CategoriesOutDto> searchCategories(String keyword);

}
