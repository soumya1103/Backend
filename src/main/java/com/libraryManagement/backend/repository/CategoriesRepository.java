package com.libraryManagement.backend.repository;

import com.libraryManagement.backend.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    List<Categories> findByCategoryName(String categoryName);
}
