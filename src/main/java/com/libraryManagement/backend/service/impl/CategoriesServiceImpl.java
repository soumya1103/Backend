package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.CategoriesInDto;
import com.libraryManagement.backend.dto.CategoriesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Categories;
import com.libraryManagement.backend.exception.ResourceAlreadyExistsException;
import com.libraryManagement.backend.mapper.CategoriesMapper;
import com.libraryManagement.backend.repository.BooksRepository;
import com.libraryManagement.backend.repository.CategoriesRepository;
import com.libraryManagement.backend.repository.IssuancesRepository;
import com.libraryManagement.backend.service.iCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements iCategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private IssuancesRepository issuancesRepository;

    @Override
    public Page<CategoriesOutDto> getCategories(Pageable pageable) {
        Page<Categories> categoryPage;
        categoryPage = categoriesRepository.findAll(pageable);

        return categoryPage.map(CategoriesMapper::mapToCategoriesDto);
    }

    @Override
    public Categories findByCategoryNameIgnoreCase(String categoryName) {
        return categoriesRepository.findByCategoryNameIgnoreCase(categoryName);
    }

    public CategoriesOutDto findById(int categoryId) {
        Categories categories = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        CategoriesOutDto categoriesOutDto = CategoriesMapper.mapToCategoriesDto(categories);


        return categoriesOutDto;

    }

    @Override
    public Categories save(Categories categories) {
        Categories existingCategory = categoriesRepository.findByCategoryNameIgnoreCase(categories.getCategoryName());

        if (existingCategory != null) {
            throw new ResourceAlreadyExistsException("Duplicate entry.");
        }

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
            Categories existingCategory = categoriesRepository.findByCategoryNameIgnoreCase(categoriesInDto.getCategoryName());

            if (existingCategory != null) {
                throw new ResourceAlreadyExistsException("Duplicate entry.");
            }
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
    public void deleteById(int categoryId) {
        Categories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Books> booksInCategory = booksRepository.findByCategoryName(category.getCategoryName());

        for (Books book : booksInCategory) {
            if (issuancesRepository.existsByBooksBookIdAndStatus(book.getBookId(), "Issued")) {
                throw new RuntimeException("Cannot delete category because a book from this category is currently issued.");
            }
        }

        booksRepository.deleteAll(booksInCategory);

        categoriesRepository.deleteById(categoryId);
    }

    @Override
    public long getCategoryCount() {
        return categoriesRepository.count();
    }

    @Override
    public List<CategoriesOutDto> getAllCategories() {
        List<CategoriesOutDto> categoriesOutDto = categoriesRepository.findAll()
                .stream().map(CategoriesMapper::mapToCategoriesDto).toList();

        return categoriesOutDto;
    }

    @Override
    public List<CategoriesOutDto> searchCategories(String keyword) {
        List<Categories> categories = categoriesRepository.findByCategoryNameContaining(keyword);
        return categories.stream().map(CategoriesMapper::mapToCategoriesDto).toList();
    }
}
