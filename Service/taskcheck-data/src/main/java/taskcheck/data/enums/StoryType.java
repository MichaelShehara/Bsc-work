/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.enums;

/**
 * The Enum StoryType.
 */
public enum StoryType {

	/** The user story. */
	USER_STORY(0, "USER_STORY", "User Story"),

	/** The technical story. */
	TECHNICAL_STORY(1, "TECHNICAL_STORY", "Technical Story");

	/** The key. */
	private int key;

	/** The value. */
	private String value;

	/** The display value. */
	private String displayValue;

	/**
	 * Instantiates a new story type.
	 *
	 * @param key the key
	 * @param value the value
	 * @param displayValue the display value
	 */
	private StoryType(int key, String value, String displayValue) {
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
