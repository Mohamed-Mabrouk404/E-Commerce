package com.global.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.repository.OrderRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/analytics")
@Log4j2
public class AnalyticsController {
	private final OrderRepo orderRepo;
	
	@GetMapping("/orders/count")
	public List<Object[]> getOrdersCount() {
		return orderRepo.getOrdersCount();
	}
	
	@GetMapping("/orders/daily-count")
	public List<Object[]> getDailyOrdersCount() {
		return orderRepo.getDailyOrdersCount();
	}
	
	@GetMapping("/orders/weekly-count")
	public List<Object[]> getWeeklyOrdersCount() {
		return orderRepo.getWeeklyOrdersCount();
	}
	
	@GetMapping("/orders/monthly-count")
	public List<Object[]> getMonthlyOrdersCount() {
		return orderRepo.getMonthlyOrdersCount();
	}
}
