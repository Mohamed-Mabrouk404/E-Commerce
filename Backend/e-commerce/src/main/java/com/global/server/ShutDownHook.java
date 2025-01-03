//package com.global.server;
//
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.global.config.SessionWebSocketHandler;
//import com.global.repository.CartRepo;
//
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Component
//public class ShutDownHook implements DisposableBean{
//	
//	@Autowired
//	private CartRepo cartRepo;
//	
//	@Autowired
//	private SessionWebSocketHandler sessionWebSocketHandler;
//
//	@Override
//	public void destroy() throws Exception {
//		// TODO Auto-generated method stub
//		log.info("SERVER IS GOING TO DIE!!");
//		//cartRepo.deleteAll();
//		//log.info("ALL CURRENT SESSION HAS GONE TO BIN!!");
//		
//	}
//
//}
