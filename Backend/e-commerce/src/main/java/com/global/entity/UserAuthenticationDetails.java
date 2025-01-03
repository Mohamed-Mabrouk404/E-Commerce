package com.global.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Setter
@Getter

@Log4j2
public class UserAuthenticationDetails{

	private String uid;
	
	private LocalDateTime uid_exp;
	
	private boolean expired = false;
}



