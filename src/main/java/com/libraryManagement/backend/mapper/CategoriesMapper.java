package com.libraryManagement.backend.mapper;

import com.libraryManagement.backend.dto.CategoriesInDto;
import com.libraryManagement.backend.dto.CategoriesOutDto;
import com.libraryManagement.backend.entity.Categories;

public class CategoriesMapper {

    public static CategoriesOutDto mapToCategoriesDto(Categories categories) {
        CategoriesOutDto categoriesDto = new CategoriesOutDto();
        categoriesDto.setCategoryName(categories.getCategoryName());
        categoriesDto.setCategoryIcon(categories.getCategoryIcon());

        return categoriesDto;
    }

    public static Categories mapToCategoriesEntity(CategoriesInDto categoriesInDto) {
        Categories categories = new Categories();
        categories.setCategoryId(categoriesInDto.getCategoryId());
        categories.setCategoryName(categoriesInDto.getCategoryName());
        categories.setCategoryIcon(categoriesInDto.getCategoryIcon());

        return categories;
    }
}
