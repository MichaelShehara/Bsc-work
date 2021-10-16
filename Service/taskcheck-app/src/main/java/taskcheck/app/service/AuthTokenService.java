/*
// * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

/**
 * The Interface AuthTokenService.
 */
public interface AuthTokenService {

	/**
	 * Gets the username from token.
	 *
	 * @param token the token
	 * @return the username from token
	 */
	public String getUsernameFromToken(String token);

	/**
	 * Gets the expiration date from token.
	 *
	 * @param token the token
	 * @return the expiration date from token
	 */
	public Date getExpirationDateFromToken(String token);

	/**
	 * Gets the claim from token.
	 *
	 * @param <T> the generic type
	 * @param token the token
	 * @param claimsResolver the claims resolver
	 * @return the claim from token
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

	/**
	 * Generate token.
	 *
	 * @param userDetails the user details
	 * @return the string
	 */
	public String generateAccessToken(UserDetails userDetails);
	
	/**
	 * Generate refresh token.
	 *
	 * @param userDetails the user details
	 * @return the string
	 */
	public String generateRefreshToken(UserDetails userDetails);

	/**
	 * Validate token.
	 *
	 * @param token the token
	 * @param userDetails the user details
	 * @return the boolean
	 */
	public Boolean validateToken(String token, UserDetails userDetails);

	/**
	 * Generate long token.
	 *
	 * @param userDetails the user details
	 * @return the string
	 */
	public String generateNoExpirationToken(UserDetails userDetails);
}
