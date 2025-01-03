package com.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.entity.UserAuthenticationDetails;
import com.global.service.UserAuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
@Log4j2
public class UserAuthenticationController {

	private final UserAuthenticationService userAuthenticationService;

	@PostMapping("/authenticate_user")
	public ResponseEntity<?> authenticateUser(@RequestBody UserAuthenticationDetails userAuthentication) {
		log.info("->>>>>>>>>>>>>>><<<<<< #authenticate user controoller");
		UserAuthenticationDetails UAD = userAuthenticationService.authenticateUser(userAuthentication);
		return ResponseEntity.ok(UAD);
	}

}
