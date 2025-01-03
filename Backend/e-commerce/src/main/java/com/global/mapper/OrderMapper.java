package com.global.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.global.dto.OrderDto;
import com.global.entity.Order;

import lombok.extern.log4j.Log4j2;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
	
	OrderDto mapToDto(Order order);
	List<OrderDto> mapToDto(List<Order> orders);
	Set<OrderDto> mapToDto(Set<Order> orders);
	
	@Mappings({
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "orderItems", ignore = true),
		@Mapping(target = "status", ignore = true)
	})
	Order mapToEntity(OrderDto orderDto);
	List<Order> mapToEntity(List<OrderDto> orderDto);
	Set<Order> mapToEntity(Set<OrderDto> orderDto);
	
}
