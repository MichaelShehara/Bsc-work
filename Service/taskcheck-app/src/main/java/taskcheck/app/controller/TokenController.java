/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import taskcheck.app.config.Constants;
import taskcheck.app.dto.TokenDto;
import taskcheck.app.service.AuthTokenService;

/**
 * The Class TokenController.
 */
@RestController
@CrossOrigin
@RequestMapping("/token")
public class TokenController {

	/** The auth token service. */
	@Autowired
	private AuthTokenService authTokenService;

	/**
	 * Creates the access token.
	 *
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@GetMapping(value = "/accessToken", headers = Constants.ApiVersion.V1)
	public ResponseEntity<TokenDto> createAccessToken() throws Exception {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) token.getPrincipal();
		final String accessToken = authTokenService.generateAccessToken(userDetails);

		TokenDto dto = new TokenDto();
		dto.setAccessToken(accessToken);

		return ResponseEntity.ok(dto);
	}

}
