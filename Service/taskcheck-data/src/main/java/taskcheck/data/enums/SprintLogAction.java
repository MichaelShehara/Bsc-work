/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.enums;

/**
 * The Enum SprintLogAction.
 */
public enum SprintLogAction {

	/** The sprint started. */
	SPRINT_STARTED(0, "SPRINT_STARTED", "Sprint Started"),

	/** The sprint completed. */
	SPRINT_COMPLETED(1, "SPRINT_COMPLETED", "Sprint Completed"),
	
	/** The scope increased. */
	SCOPE_INCREASED(2, "SCOPE_INCREASED", "Scope Increased"),
	
	/** The scope reduced. */
	SCOPE_REDUCED(3, "SCOPE_REDUCED", "Scope Reduced"),
	
	/** The task completed. */
	TASK_COMPLETED(4, "TASK_COMPLETED", "Task Completed"),
	
	/** The issue completed. */
	ISSUE_COMPLETED(5, "ISSUE_COMPLETED", "Sprint Completed");

	/** The key. */
	private int key;

	/** The value. */
	private String value;

	/** The display value. */
	private String displayValue;

	/**
	 * Instantiates a new sprint log action.
	 *
	 * @param key the key
	 * @param value the value
	 * @param displayValue the display value
	 */
	private SprintLogAction(int key, String value, String displayValue) {
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
