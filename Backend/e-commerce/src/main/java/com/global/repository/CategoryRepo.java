package com.global.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.entity.Category;
import com.global.projection.CategorySummary;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{

	public Category findByName(String name);
	
	@Query("select c.name from Category c")
	public List<String> findAllNames();
	
	@Query("SELECT category FROM Category category")
	public List<CategorySummary> getCategoriesSummary();
	
	@Query("SELECT category FROM Category category")
	public Page<CategorySummary> getCategoriesSummary(Pageable pageable);
	
	@Query("SELECT category FROM Category category WHERE category.id = :id")
	public CategorySummary getCategorySummary(Long id);
	
}
