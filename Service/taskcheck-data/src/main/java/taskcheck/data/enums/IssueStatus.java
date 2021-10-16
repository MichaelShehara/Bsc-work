/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.enums;

/**
 * The Enum IssueStatus.
 */
public enum IssueStatus {

	/** The open. */
	OPEN(0, "OPEN", "Open", "COMMON"),

	/** The in progress. */
	IN_PROGRESS(1, "IN_PROGRESS", "In Progress", "DEV"),

	/** The reopened. */
	REOPENED(2, "REOPENED", "Reopened", "QA"),

	/** The resolved. */
	RESOLVED(3, "RESOLVED", "Resolved", "DEV"),

	/** The closed. */
	CLOSED(4, "CLOSED", "Closed", "QA"),

	/** The not a defect. */
	NOT_A_DEFECT(5, "NOT_A_DEFECT", "Not a Defect", "DEV"),

	/** The as designed. */
	AS_DESIGNED(6, "AS_DESIGNED", "As Designed", "DEV");

	/** The key. */
	private int key;

	/** The value. */
	private String value;

	/** The display value. */
	private String displayValue;

	/** The type. */
	private String type;

	/**
	 * Instantiates a new issue status.
	 *
	 * @param key the key
	 * @param value the value
	 * @param displayValue the display value
	 * @param type the type
	 */
	private IssueStatus(int key, String value, String displayValue, String type) {
		this.key = key;
		this.value = value;
		this.displayValue = displayValue;
		this.type = type;
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

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
