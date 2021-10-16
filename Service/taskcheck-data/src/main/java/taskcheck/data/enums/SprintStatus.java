/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.enums;

/**
 * The Enum SprintStatus.
 */
public enum SprintStatus {

	/** The not started. */
	NOT_STARTED(0, "NOT_STARTED", "Not Started"),

	/** The started. */
	STARTED(1, "STARTED", "Started"),

	/** The completed. */
	COMPLETED(2, "COMPLETED", "Completed"),

	/** The cancelled. */
	CANCELLED(3, "CANCELLED", "Cancelled");

	/** The key. */
	private int key;

	/** The value. */
	private String value;

	/** The display value. */
	private String displayValue;

	/**
	 * Instantiates a new sprint status.
	 *
	 * @param key          the key
	 * @param value        the value
	 * @param displayValue the display value
	 */
	private SprintStatus(int key, String value, String displayValue) {
		this.key = key;
		this.value = value;
		this.displayValue = displayValue;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the display value.
	 *
	 * @return the display value
	 */
	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * Sets the display value.
	 *
	 * @param displayValue the new display value
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

}
