package com.global.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.global.dto.ProductWithCategoryDto;
import com.global.entity.Product;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CategoryMapper.class})
public interface ProductWithCategoryMapper {

	ProductWithCategoryDto mapToDto(Product product);
	List<ProductWithCategoryDto> mapToDto(List<Product> products);
	Set<ProductWithCategoryDto> mapToDto(Set<Product> products);
	
	@Mappings({
		@Mapping(target = "purchaseCount" , ignore = true),
		@Mapping(target = "stockQuantity" , ignore = true),
		@Mapping(target = "stockWeight" , ignore = true),
		@Mapping(target = "discountedPrice" , ignore = true)
	})
	Product mapToEntity(ProductWithCategoryDto productDto);
	List<Product> mapToEntity(List<ProductWithCategoryDto> productDto);
	Set<Product> mapToEntity(Set<ProductWithCategoryDto> productDto);
	
	@AfterMapping
	public default void setDiscountedPrice(@MappingTarget Product product, ProductWithCategoryDto productDto) {
		product.setDiscountedPrice(product.getOriginalPrice());
	}
	
}
