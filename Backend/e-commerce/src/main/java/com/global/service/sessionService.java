package com.global.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class sessionService {
	public void sleepSession(HttpSession session) {
		log.info("\nprev. MII: " + session.getMaxInactiveInterval() + "\n" +
				 "prev. LAT: " + session.getLastAccessedTime() + "\n" + 
				 "prev.  CT: " + session.getCreationTime());
		
		session.setMaxInactiveInterval(5 * 60);
		
		log.info("\ncurr. MII: " + session.getMaxInactiveInterval() + "\n" +
				 "curr. LAT: " + session.getLastAccessedTime() + "\n" + 
				 "curr.  CT: " + session.getCreationTime());
	}
}
