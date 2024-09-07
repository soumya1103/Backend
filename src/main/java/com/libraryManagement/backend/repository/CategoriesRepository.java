package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    Page<Categories> findAll(Pageable pageable);

    long count();

    Categories findByCategoryNameIgnoreCase(String categoryName);

    List<Categories> findByCategoryNameContaining(String category);

    void deleteByCategoryName(String categoryName);
}
