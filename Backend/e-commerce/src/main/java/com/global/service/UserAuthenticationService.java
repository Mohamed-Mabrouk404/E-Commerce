package com.global.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.global.entity.UserAuthenticationDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserAuthenticationService {
	@Value("${UID_EXP_TIME_IN_MINUTES}")
	private long uidExpTime;

	private final CartService cartService;

	public UserAuthenticationDetails authenticateUser(UserAuthenticationDetails userAuthentication) {
		log.info("user authentication service");

		String uid = userAuthentication.getUid();
		LocalDateTime uid_exp = userAuthentication.getUid_exp();

		if (uid == null || uid_exp == null) {
			log.info("null");
			uid = UUID.randomUUID().toString();
			uid_exp = LocalDateTime.now().plusMinutes(uidExpTime);

			cartService.createCart(uid, uid_exp);
		} else { // check time validity

			if (LocalDateTime.now().isAfter(uid_exp)) { // expired
				uid_exp = LocalDateTime.now().plusMinutes(uidExpTime);
				
				if (cartService.getCartByCid(uid) == null) { // cart removed from db
					log.info("expired : removed");

					cartService.createCart(uid, uid_exp);
				} else { // cart still in db
					log.info("expired : still");
					
					cartService.cleanCart(uid);
					cartService.updateCartExpTime(uid, uid_exp);
				}
				userAuthentication.setExpired(true);

			} else { // not expired
				log.info("not expired");
				uid_exp = LocalDateTime.now().plusMinutes(uidExpTime);
				cartService.updateCartExpTime(uid, uid_exp);
			}
		}

		userAuthentication.setUid(uid);
		userAuthentication.setUid_exp(uid_exp);

		return userAuthentication;
	}

}
