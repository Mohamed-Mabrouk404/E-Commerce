package com.global.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.global.entity.Cart;
import com.global.entity.CartItem;
import com.global.entity.Order;
import com.global.entity.OrderItem;
import com.global.projection.OrderDetails;
import com.global.projection.OrderSummary;
import com.global.repository.CartRepo;
import com.global.repository.OrderRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService {

	private final OrderRepo orderRepo;
	private final CartRepo cartRepo;
	
	// admin behave
	public List<OrderSummary> getOrdersSummary(int page, int size, String sortBy, boolean isAsc){
		Pageable pageable = PageRequest.of(page, size, 
				Sort.by(isAsc ? Direction.ASC : Direction.DESC, sortBy));
		
		log.info("page : " + page + " size : " + size + " sortBy : " 
		+ sortBy + " isAsc : " + isAsc);
		
		Page<OrderSummary> ordersSummary = orderRepo.getOrdersSummary(pageable);
		
		return ordersSummary.getContent();
	}
	
	// admin behave
	public List<OrderSummary> getOrdersSummary(String status) {
		log.info("status 2-> " + status);
		return orderRepo.getOrdersSummary(status);
	}	
	
	// admin behave
	public OrderDetails getOrderDetails(Long id) {
		return orderRepo.getOrderDetails(id);
	}
	
	// user behave
	@Transactional
	public Order insert(Order order, String cid) {
		Cart cart = cartRepo.findByCid(cid);
		
		order.setTotalPrice(cart.getTotalPrice());
		order.setItemsNum(cart.getItemsNum());
		
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setWeight(cartItem.getWeight());
            orderItem.setPrice(cartItem.getPrice());
            
            if(orderItem.getProduct().isBuyWithWeight()) {
            	log.info("orderItem.getProduct().name : " + orderItem.getProduct().getName());
            	log.info("orderItem.getProduct().weight : " + orderItem.getWeight());
            	
            	orderItem.getProduct().decreaseStockWeightBy(orderItem.getQuantity() * orderItem.getWeight());
            }else {
            	log.info("insert order decrease stock quantity");
            	orderItem.getProduct().decreaseStockQuantityBy(orderItem.getQuantity());
            	log.info("stock quantity : " + orderItem.getProduct().getStockQuantity());
            }
            order.addOrderItem(orderItem);
        }
		return orderRepo.save(order);
	}
	
	@Transactional
	public Order updateStatus(String status, Long orderId) {
		Order order = orderRepo.findById(orderId).get();
		
		if(status.equals("completed")) {
			for (OrderItem orderItem : order.getOrderItems()) {
				orderItem.getProduct().increasePurchaseCount();
			}
		}else if(status.equals("canceled")) { // orderItems returned to stock again
			for (OrderItem orderItem : order.getOrderItems()) {
	            if(orderItem.getProduct().isBuyWithWeight()) {
	            	orderItem.getProduct().increaseStockWeightBy(orderItem.getQuantity() * orderItem.getWeight());
	            }else {
	            	orderItem.getProduct().increaseStockQuantityBy(orderItem.getQuantity());
	            }
			}
		}
		
		order.setStatus(status);
		return orderRepo.save(order);
	}
	
	public Order update(Order order) {
		return orderRepo.save(order);
	}
	
	public void deleteById(Long id){
		orderRepo.deleteById(id);
	}
	
	public void delete(Order order){
		orderRepo.delete(order);
	}
}
