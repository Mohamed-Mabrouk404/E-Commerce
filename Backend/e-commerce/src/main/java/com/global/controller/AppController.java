package com.global.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/app")
@Log4j2
public class AppController {
	
    @GetMapping("/getSessionId")
    public String getSessionId(HttpSession session) {
        // Retrieve the session ID
    	log.info("session id: " + session.getId());
        return "Session ID: " + session.getId();
    }
    	
}
