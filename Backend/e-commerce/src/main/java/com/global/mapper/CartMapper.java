package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.CartDto;
import com.global.entity.Cart;
import com.global.entity.Product;

@Mapper(componentModel = "spring",  uses = {CartItemMapper.class})
public interface CartMapper {
	
	CartDto mapToDto(Cart cart);
	List<CartDto> mapToDto(List<Cart> carts);
	
	Cart mapToEntity(CartDto cartDto);
	List<Cart> mapToEntity(List<CartDto> cartDto);
	
	void mapEntityToEntity(@MappingTarget Cart currentCart, Cart newCart);
	
}
