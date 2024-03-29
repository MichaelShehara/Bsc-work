/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * The Class AppAuthenticationEntryPoint.
 */
@Component
public class AppAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 241903209407740209L;

	/**
	 * Commence.
	 *
	 * @param request       the request
	 * @param response      the response
	 * @param authException the auth exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}