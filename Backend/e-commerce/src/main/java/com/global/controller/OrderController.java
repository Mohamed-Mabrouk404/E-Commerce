package com.global.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.global.dto.OrderDto;
import com.global.entity.Order;
import com.global.mapper.OrderMapper;
import com.global.projection.OrderDetails;
import com.global.projection.OrderSummary;
import com.global.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
@Log4j2
public class OrderController {

	private final OrderService orderService;

	private final OrderMapper orderMapper;

	private final ObjectMapper objectMapper;

	@GetMapping()
	public List<OrderSummary> findAllOrders(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "lastModifiedDate") String sortBy,
			@RequestParam(defaultValue = "true") boolean isAsc) {

		List<OrderSummary> allOrders = orderService.getOrdersSummary(page - 1, size, sortBy, isAsc);

		log.info("all orders length: " + allOrders.size());

		return allOrders;
	}

	@GetMapping("/status/{status}")
	public List<OrderSummary> findOrdersByStatus(@PathVariable String status) {
		log.info("status 1-> " + status);
		List<OrderSummary> Orders = orderService.getOrdersSummary(status);
		return Orders;
	}

	@GetMapping("/{id}")
	public OrderDetails findById(@PathVariable Long id) {
		OrderDetails order = orderService.getOrderDetails(id);
		return order;
	}

	@PostMapping("/add")
	public OrderDto createOrder(@RequestBody OrderDto orderDto, @RequestHeader("uid") String cid) {

		try {
			log.info("orderFetails::::", objectMapper.writeValueAsString(orderDto));
			log.info("         uid::::", cid);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Order newOrder = orderMapper.mapToEntity(orderDto);

		newOrder = orderService.insert(newOrder, cid);
		return orderMapper.mapToDto(newOrder);
	}

	@PutMapping("/update")
	public OrderDto updateStatus(@RequestParam String status, @RequestParam Long orderId) {
		return orderMapper.mapToDto(orderService.updateStatus(status, orderId));
	}

	@DeleteMapping("/remove/{id}")
	public String deleteById(@PathVariable Long id) {
		orderService.deleteById(id);
		return "Category with id = " + id + " has been removed.";
	}

}
