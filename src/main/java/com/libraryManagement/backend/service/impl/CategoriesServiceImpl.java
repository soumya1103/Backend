package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.CategoriesInDto;
import com.libraryManagement.backend.dto.CategoriesOutDto;
import com.libraryManagement.backend.entity.Categories;
import com.libraryManagement.backend.mapper.CategoriesMapper;
import com.libraryManagement.backend.repository.CategoriesRepository;
import com.libraryManagement.backend.service.iCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements iCategoriesService {

    private CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    public List<CategoriesOutDto> findAll() {
        List<CategoriesOutDto> categoriesOutDto = categoriesRepository.findAll()
                .stream().map(CategoriesMapper::mapToCategoriesDto).toList();

        return categoriesOutDto;
    }

    @Override
    public Categories findByCategoryNameIgnoreCase(String categoryName) {
        return categoriesRepository.findByCategoryNameIgnoreCase(categoryName);
    }

    public Optional<CategoriesOutDto> findById(int categoryId) {
        Optional<CategoriesOutDto> categoriesOutDto = Optional.ofNullable(categoriesRepository.findById(categoryId)
                .map(CategoriesMapper::mapToCategoriesDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + categoryId)));

        return categoriesOutDto;

    }

    @Override
    public Categories save(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public CategoriesOutDto updateCategory(CategoriesInDto categoriesInDto) {
        Optional<Categories> categoriesOptional = categoriesRepository.findById(categoriesInDto.getCategoryId());

        if(!categoriesOptional.isPresent()) {
            throw new RuntimeException("Category not found");
        }

        Categories categoryToUpdate = categoriesOptional.get();

        if (categoriesInDto.getCategoryName() != null && !categoriesInDto.getCategoryName().isEmpty()) {
            categoryToUpdate.setCategoryName(categoriesInDto.getCategoryName());
        }

        if (categoriesInDto.getCategoryIcon() != null && !categoriesInDto.getCategoryIcon().isEmpty()) {
            categoryToUpdate.setCategoryIcon(categoriesInDto.getCategoryIcon());
        }

        Categories updatedCategory = categoriesRepository.save(categoryToUpdate);

        CategoriesOutDto categoriesOutDto = new CategoriesOutDto();
        categoriesOutDto.setCategoryName(updatedCategory.getCategoryName());
        categoriesOutDto.setCategoryIcon(updatedCategory.getCategoryIcon());

        return categoriesOutDto;
    }

    @Override
    public CategoriesOutDto updateCategoryByName(String categoryName, CategoriesInDto categoryInDto) {
        Categories category = categoriesRepository.findByCategoryNameIgnoreCase(categoryName);

        if (category != null) {
            if (categoryInDto.getCategoryName() != null && !categoryInDto.getCategoryName().isEmpty()) {
                category.setCategoryName(categoryInDto.getCategoryName());
            }

            if (categoryInDto.getCategoryIcon() != null && !categoryInDto.getCategoryIcon().isEmpty()) {
                category.setCategoryIcon(categoryInDto.getCategoryIcon());
            }

            Categories updatedCategory = categoriesRepository.save(category);
            return CategoriesMapper.mapToCategoriesDto(updatedCategory);
        }

        return null;
    }

    @Override
    public void deleteById(int categoryId) {
        categoriesRepository.deleteById(categoryId);
    }

    @Override
    public long getCategoryCount() {
        return categoriesRepository.count();
    }

    @Override
    public void deleteByCategoryName(String categoryName) {
        categoriesRepository.deleteByCategoryName(categoryName);
    }
}
