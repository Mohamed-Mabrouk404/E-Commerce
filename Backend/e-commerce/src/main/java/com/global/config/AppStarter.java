package com.global.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AppStarter implements CommandLineRunner{
	
//	@Autowired
//	private HttpSession httpSession;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
//		log.info("session id: " + httpSession.getId());
		
	}

}
