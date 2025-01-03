package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.global.dto.CategoryDto;
import com.global.dto.CategoryWithProductsDto;
import com.global.entity.Category;
import com.global.projection.CategorySummary;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryWithProductsMapper {
	
	CategoryWithProductsDto mapToDto(Category category);
	CategoryWithProductsDto mapToDto(CategorySummary categorySummary);
	List<CategoryWithProductsDto> mapToDto(List<Category> categories);
	
	
	Category mapToEntity(CategoryWithProductsDto categoryDto);
	List<Category> mapToEntity(List<CategoryWithProductsDto> categoryDto);
	
}
