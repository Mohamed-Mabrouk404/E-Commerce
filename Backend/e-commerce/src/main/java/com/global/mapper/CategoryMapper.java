package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.global.dto.CategoryDto;
import com.global.entity.Category;
import com.global.projection.CategorySummary;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	CategoryDto mapToDto(Category category);
	List<CategoryDto> mapToDto(List<Category> categories);
	
	@Mappings({
		@Mapping(target = "productsNum", ignore = true),
		@Mapping(target = "products", ignore = true)
	})
	Category mapToEntity(CategoryDto categoryDto);
	List<Category> mapToEntity(List<CategoryDto> categoryDto);
		
}
