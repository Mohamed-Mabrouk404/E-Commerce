package com.global.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.entity.Order;
import com.global.projection.OrderDetails;
import com.global.projection.OrderSummary;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
	
	@Query("SELECT order FROM Order order")
	public Page<OrderSummary> getOrdersSummary(Pageable pageable);
	
	@Query("SELECT order FROM Order order WHERE order.status = :status")
	public List<OrderSummary> getOrdersSummary(String status);
	
	@Query("SELECT order FROM Order order WHERE order.id = :id")
	public OrderDetails getOrderDetails(Long id);
	
	
	
//	--------------------------------------------
//	Analytics part
	
	@Query("SELECT order.status, COUNT(order) "
			+ "FROM Order order "
			+ "GROUP BY order.status")
	public List<Object[]> getOrdersCount();
	
	@Query("SELECT FUNCTION('DATE', order.createdDate), order.status, COUNT(order) "
			+ "FROM Order order "
			+ "GROUP BY FUNCTION('DATE', order.createdDate), order.status")
	public List<Object[]> getDailyOrdersCount();
	
	@Query(value = "SELECT getWeekOfYear(o.created_date), o.status, count(*) "
			+ "FROM orders o "
			+ "GROUP BY getWeekOfYear(o.created_date) , o.status", nativeQuery = true)
	public List<Object[]> getWeeklyOrdersCount();
	
	@Query("SELECT FUNCTION('DATE_FORMAT', order.createdDate, '%Y-%m'), order.status, COUNT(order) "
			+ "FROM Order order "
			+ "GROUP BY FUNCTION('DATE_FORMAT', order.createdDate, '%Y-%m'), order.status")
	public List<Object[]> getMonthlyOrdersCount();
	
}
