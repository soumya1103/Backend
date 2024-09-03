package com.libraryManagement.backend.service;

import com.libraryManagement.backend.dto.CategoriesInDto;
import com.libraryManagement.backend.dto.CategoriesOutDto;
import com.libraryManagement.backend.entity.Categories;

import java.util.List;

public interface iCategoriesService {

    List<CategoriesOutDto> findAll();

    Categories findByCategoryNameIgnoreCase(String categoryName);

    CategoriesOutDto findById(int categoryId);

    Categories save(Categories categories);

    CategoriesOutDto updateCategory(CategoriesInDto categoriesInDto);

    CategoriesOutDto updateCategoryByName(String categoryName, CategoriesInDto categoryInDto);

    void deleteById(int categoryId);

    long getCategoryCount();

    void deleteByCategoryName(String categoryName);

}
