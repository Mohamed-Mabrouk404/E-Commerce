package com.global.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.dto.CategoryDto;
import com.global.dto.CategoryWithProductsDto;
import com.global.entity.Category;
import com.global.entity.Product;
import com.global.mapper.CategoryMapper;
import com.global.mapper.CategoryWithProductsMapper;
import com.global.mapper.ProductMapper;
import com.global.projection.CategorySummary;
import com.global.service.CategoryService;
import com.global.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
@Log4j2
public class CategoryController {

	private final CategoryService categoryService;
	private final ProductService productService;

	private final CategoryMapper categoryMapper;
	private final ProductMapper productMapper;
	private final CategoryWithProductsMapper categoryWithProductsMapper;

	@GetMapping("/summary")
	public List<CategorySummary> getAllCategoriesSummary(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "null") String sortBy,
			@RequestParam(defaultValue = "false") boolean isAsc) {

		List<CategorySummary> categorySummaries = categoryService.getAllCategories(page - 1, size, sortBy, isAsc);

		return categorySummaries;
	}

	@GetMapping("")
	public List<CategoryWithProductsDto> getAllCategoriesDetails(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "null") String sortBy,
			@RequestParam(defaultValue = "false") boolean isAsc) {

		List<CategorySummary> categorySummaries = categoryService.getAllCategories();
		CategoryWithProductsDto Dto;
		List<CategoryWithProductsDto> categoriesDetails = new ArrayList<>();

		for (CategorySummary categorySummary : categorySummaries) {
			Page<Product> categoryProducts = productService.getCategoryProducts(categorySummary.getId(), page - 1, size,
					sortBy, isAsc);

			// LikedHashSet reserves the order of elements
			Set<Product> products = new LinkedHashSet<>(categoryProducts.getContent());

			// combining category with its products in one DTO
			Dto = categoryWithProductsMapper.mapToDto(categorySummary);
			Dto.setProducts(productMapper.mapToDto(products));

			categoriesDetails.add(Dto);
		}

		return categoriesDetails;
	}

	@GetMapping("/category/products")
	public CategoryWithProductsDto getCategoryDetailsById(@RequestParam Long id,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "null") String sortBy, @RequestParam(defaultValue = "false") boolean isAsc) {

		CategorySummary categorySummary = categoryService.getCategoryById(id);

		// we need to fetch its products here
		Page<Product> categoryProducts = productService.getCategoryProducts(id, page - 1, size, sortBy, isAsc);

		// LikedHashSet reserves the order of elements
		Set<Product> products = new LinkedHashSet<>(categoryProducts.getContent());

		// combining category with its products in one DTO
		CategoryWithProductsDto Dto = categoryWithProductsMapper.mapToDto(categorySummary);
		Dto.setProducts(productMapper.mapToDto(products));

		return Dto;
	}

	@PostMapping("/add")
	public CategoryDto insert(@RequestBody CategoryDto categoryDto) {
		Category category = categoryService.insert(categoryMapper.mapToEntity(categoryDto));
		return categoryMapper.mapToDto(category);
	}

	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable Long id) {
		categoryService.deleteById(id);
		return "Category with id = " + id + " has been removed.";
	}

	@PutMapping("/update")
	public CategoryDto update(@RequestBody CategoryDto categoryDto) {
		Category category = categoryService.update(categoryMapper.mapToEntity(categoryDto));
		return categoryMapper.mapToDto(category);
	}

}
