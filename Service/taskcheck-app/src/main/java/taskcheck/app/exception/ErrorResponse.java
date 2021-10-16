/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The Class ErrorResponse.
 */
public class ErrorResponse {

	/** The time. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date time;

	/** The message. */
	private String message;

	/** The details. */
	private String details;

	/**
	 * Instantiates a new error response.
	 *
	 * @param time    the time
	 * @param message the message
	 * @param details the details
	 */
	public ErrorResponse(Date time, String message, String details) {
		this.time = time;
		this.message = message;
		this.details = details;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(Date time) {
		this.time = time;
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

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details the new details
	 */
	public void setDetails(String details) {
		this.details = details;
	}

}
