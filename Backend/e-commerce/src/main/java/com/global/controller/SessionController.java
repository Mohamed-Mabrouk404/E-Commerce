package com.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.service.sessionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Log4j2
public class SessionController {

	private final sessionService sessionService;

	@PostMapping("/createSession")
	public ResponseEntity<?> createSession(HttpSession session) {
		log.info("session created in controller!");

		log.info("\n------------ in create session -----------------\n" + "curr. MII: "
				+ session.getMaxInactiveInterval() + "\n" + "curr. LAT: " + session.getLastAccessedTime() + "\n"
				+ "curr.  CT: " + session.getCreationTime() + "\n -----------------------------------------");

		return ResponseEntity.ok(session.getId());
	}

	@PostMapping("/sleepSession")
	public ResponseEntity<?> sleepSession(HttpSession session) {
		log.info("session sleep in controller!");
		sessionService.sleepSession(session);
		return ResponseEntity.ok(session.getId());
	}

	@GetMapping("/getSessionId")
	public ResponseEntity<?> getSession(HttpSession session) {
		return ResponseEntity.ok(session.getId());
	}
}
