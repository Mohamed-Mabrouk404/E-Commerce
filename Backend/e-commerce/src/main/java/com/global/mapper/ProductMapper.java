package com.global.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

import com.global.dto.ProductDto;
import com.global.entity.Product;

@Mapper(componentModel = "spring", uses = {OfferMapper.class})
public interface ProductMapper {

	ProductDto mapToDto(Product product);
	List<ProductDto> mapToDto(List<Product> products);
	Set<ProductDto> mapToDto(Set<Product> products);
	
	@Mappings({
		@Mapping(target = "id" , ignore = true),
		@Mapping(target = "purchaseCount" , ignore = true),
		@Mapping(target = "stockQuantity" , ignore = true),
		@Mapping(target = "stockWeight" , ignore = true),
		@Mapping(target = "category" , ignore = true)
	})
	Product mapToEntity(ProductDto productDto);
	List<Product> mapToEntity(List<ProductDto> productDto);
	Set<Product> mapToEntity(Set<ProductDto> productDto);
	
	@Mappings({
		@Mapping(target = "category" , ignore = true),
		@Mapping(target = "imageUrl" , ignore = true)
	})
	void mapEntityToEntity(@MappingTarget Product currentProduct, Product newProduct);
}
