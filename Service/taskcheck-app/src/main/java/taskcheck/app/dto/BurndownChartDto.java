/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The Class BurndownChartDto.
 */
public class BurndownChartDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5197680688118668806L;

	/** The recorded at. */
	@JsonFormat(timezone = "IST", pattern = "dd-MM-yyyy HH:mm:ss")
	private Date recordedAt;

	/** The value. */
	private float value;

	/**
	 * Gets the recorded at.
	 *
	 * @return the recorded at
	 */
	public Date getRecordedAt() {
		return recordedAt;
	}

	/**
	 * Sets the recorded at.
	 *
	 * @param recordedAt the new recorded at
	 */
	public void setRecordedAt(Date recordedAt) {
		this.recordedAt = recordedAt;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public float getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(float value) {
		this.value = value;
	}

}
