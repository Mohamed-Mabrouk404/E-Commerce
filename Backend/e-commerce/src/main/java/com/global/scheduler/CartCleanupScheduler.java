//package com.global.scheduler;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.global.repository.CartRepo;
//
//@Component
//public class CartCleanupScheduler {
//	
//	private final long CleanRate_15M = 15 * 60 * 1000; 
//	private final long CleanRate_30M = 30 * 60 * 1000; 
//	
//	@Autowired
//	private CartRepo cartRepo;
//
//	@Scheduled(fixedRate = CleanRate_15M) // clean every 15 Minute
//	public void cleanUpOldCarts() {
//        LocalDateTime cutoff = LocalDateTime.now().minus(30, ChronoUnit.MINUTES); // Carts older than 30 minutes
//        cartRepo.deleteByLastModifiedDateBefore(cutoff);
//	}
//}
