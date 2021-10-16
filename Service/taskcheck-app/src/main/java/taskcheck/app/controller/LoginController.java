/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import taskcheck.app.config.Constants;
import taskcheck.app.dto.AuthRequestDto;
import taskcheck.app.dto.AuthResponseDto;
import taskcheck.app.dto.AuthRole;
import taskcheck.app.dto.AuthUser;
import taskcheck.app.service.AuthTokenService;
import taskcheck.data.entity.User;

/**
 * The Class AuthenticationController.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login/")
public class LoginController {

	/** The authentication manager. */
	@Autowired
	private AuthenticationManager authenticationManager;

	/** The auth token service. */
	@Autowired
	private AuthTokenService authTokenService;

	/** The user details service. */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Creates the authentication token.
	 *
	 * @param requestDto the request dto
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping(value = "/", headers = Constants.ApiVersion.V1)
	public ResponseEntity<AuthResponseDto> auth(@RequestBody AuthRequestDto requestDto) throws Exception {

		authenticate(requestDto.getUsername(), requestDto.getPassword());
		
		System.out.println("sdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdfdf");
		System.out.println(requestDto.getUsername());

		List<String> privilegeList = new ArrayList<>();

		final UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());

		AuthUser authUser = (AuthUser) userDetails;
		User user = authUser.getUser();

		List<AuthRole> roleList = (List<AuthRole>) authUser.getAuthorities();

		for (AuthRole role : roleList) {
			privilegeList.add(role.getAuthority());
		}

		final String accessToken = authTokenService.generateAccessToken(userDetails);
		final String refreshToken = authTokenService.generateRefreshToken(userDetails);
		final String NoExpiryToken = authTokenService.generateNoExpirationToken(userDetails);

		AuthResponseDto dto = new AuthResponseDto();
		dto.setAccessToken(accessToken);
		dto.setRefreshToken(refreshToken);
		dto.setNoExpiryToken(NoExpiryToken);
		dto.setUserId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setFullName(user.getFullName());
		dto.setRoles(privilegeList);

		return ResponseEntity.ok(dto);
	}

	/**
	 * Authenticate.
	 *
	 * @param username the username
	 * @param password the password
	 * @throws Exception the exception
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
