/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.dto;

/**
 * The Class Transition.
 */
public class TransitionDto {

	/** The state. */
	private int state;

	/** The comment. */
	private String comment;

	/** The fix version. */
	private long fixVersion;

	/** The assignee. */
	private long assignee;

	/** The impacted area. */
	private String impactedArea;

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the fix version.
	 *
	 * @return the fix version
	 */
	public long getFixVersion() {
		return fixVersion;
	}

	/**
	 * Sets the fix version.
	 *
	 * @param fixVersion the new fix version
	 */
	public void setFixVersion(long fixVersion) {
		this.fixVersion = fixVersion;
	}

	/**
	 * Gets the assignee.
	 *
	 * @return the assignee
	 */
	public long getAssignee() {
		return assignee;
	}

	/**
	 * Sets the assignee.
	 *
	 * @param assignee the new assignee
	 */
	public void setAssignee(long assignee) {
		this.assignee = assignee;
	}

	/**
	 * Gets the impacted area.
	 *
	 * @return the impacted area
	 */
	public String getImpactedArea() {
		return impactedArea;
	}

	/**
	 * Sets the impacted area.
	 *
	 * @param impactedArea the new impacted area
	 */
	public void setImpactedArea(String impactedArea) {
		this.impactedArea = impactedArea;
	}

}
