package com.global.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.global.dto.CartItemDto;
import com.global.entity.CartItem;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartItemMapper {
	
	CartItemDto mapToDto(CartItem cartItem);
	List<CartItemDto> mapToDto(List<CartItem> cartItems);
	Set<CartItemDto> mapToDto(Set<CartItem> cartItems);
	
	CartItem mapToEntity(CartItemDto cartItemDto);
	List<CartItem> mapToEntity(List<CartItemDto> cartItemDto);
	Set<CartItem> mapToEntity(Set<CartItemDto> cartItemDto);
	
	
	@Mappings(
			{@Mapping(target = "id" , ignore = true),
			@Mapping(target = "cart" , ignore = true),
			@Mapping(target = "product" , ignore = true),
			@Mapping(target = "price" , ignore = true)}
	)
	void mapEntityToEntity(@MappingTarget CartItem currentCartItem, CartItem newCartItem);
}
