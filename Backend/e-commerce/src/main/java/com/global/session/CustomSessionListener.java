package com.global.session;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CustomSessionListener implements HttpSessionListener{
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.info("session created successfully and its id: " + se.getSession().getId());
//		cartService.createCart(se.getSession().getId());
    }
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.info("session is about to be deleted and its id: " + se.getSession().getId());
//		cartService.removeCart(se.getSession().getId());
    }
}
