package com.global.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.global.entity.Category;
import com.global.projection.CategorySummary;
import com.global.repository.CategoryRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

	private final CategoryRepo categoryRepo;
	
	public List<CategorySummary> getAllCategories(){
		return categoryRepo.getCategoriesSummary();
	}
	
	public List<CategorySummary> getAllCategories(int page, int size, String sortBy, boolean isAsc){
		Pageable pageable;
		if(!sortBy.equals("null")) {
			pageable = PageRequest.of(page, size, 
					Sort.by(isAsc ? Direction.ASC : Direction.DESC, sortBy));
		}else {
			pageable = PageRequest.of(page, size);
		}
		
		return categoryRepo.getCategoriesSummary(pageable).getContent();
	}
	
	public CategorySummary getCategoryById(Long id) {
		return categoryRepo.getCategorySummary(id);
	}
	
	public List<String> findAllNames(){
		return categoryRepo.findAllNames();
	}
	
	public Category insert(Category category) {
		return categoryRepo.save(category);
	}
	
	public Category update(Category category) {
		return categoryRepo.save(category);
	}
	
	public void deleteById(Long id){
		categoryRepo.deleteById(id);
	}
	
	public void delete(Category category){
		categoryRepo.delete(category);
	}
	
	public Category findByName(String name) {
		return categoryRepo.findByName(name);
	}
	

}
