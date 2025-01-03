package com.global.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.global.dto.OrderItemDto;
import com.global.entity.OrderItem;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
	
	@Mappings({
		@Mapping(target = "order", ignore = true)
	})
	OrderItemDto mapToDto(OrderItem orderItem);
	List<OrderItemDto> mapToDto(List<OrderItem> orderItems);
	Set<OrderItemDto> mapToDto(Set<OrderItem> orderItems);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "order", ignore = true)
	})
	OrderItem mapToEntity(OrderItemDto orderItemDto);
	List<OrderItem> mapToEntity(List<OrderItemDto> orderItemDto);
	Set<OrderItem> mapToEntity(Set<OrderItemDto> orderItemDto);
	
}
