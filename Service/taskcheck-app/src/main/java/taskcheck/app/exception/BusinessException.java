/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.exception;

import org.springframework.http.HttpStatus;

/**
 * The Class BusinessException.
 */
public class BusinessException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6128231263570036954L;

	/** The code. */
	private HttpStatus code;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new business exception.
	 *
	 * @param code the code
	 * @param message the message
	 */
	public BusinessException(HttpStatus code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public HttpStatus getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(HttpStatus code) {
		this.code = code;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
