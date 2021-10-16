/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.dto;

import java.io.Serializable;

/**
 * The Class TokenDto.
 */
public class TokenDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -351915200259897924L;

	/** The access token. */
	private String accessToken;

	/**
	 * Gets the access token.
	 *
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the access token.
	 *
	 * @param accessToken the new access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
