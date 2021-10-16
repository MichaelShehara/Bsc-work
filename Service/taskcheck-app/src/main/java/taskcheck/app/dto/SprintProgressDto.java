/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.dto;

import java.io.Serializable;

/**
 * The Class SprintProgressDto.
 */
public class SprintProgressDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4864547390768177655L;

	/** The todo. */
	private int todo;

	/** The inprogress. */
	private int inprogress;

	/** The completed. */
	private int completed;
	
	

	/**
	 * Gets the todo.
	 *
	 * @return the todo
	 */
	public int getTodo() {
		return todo;
	}

	/**
	 * Sets the todo.
	 *
	 * @param todo the new todo
	 */
	public void setTodo(int todo) {
		this.todo = todo;
	}

	/**
	 * Gets the inprogress.
	 *
	 * @return the inprogress
	 */
	public int getInprogress() {
		return inprogress;
	}

	/**
	 * Sets the inprogress.
	 *
	 * @param inprogress the new inprogress
	 */
	public void setInprogress(int inprogress) {
		this.inprogress = inprogress;
	}

	/**
	 * Gets the completed.
	 *
	 * @return the completed
	 */
	public int getCompleted() {
		return completed;
	}

	/**
	 * Sets the completed.
	 *
	 * @param completed the new completed
	 */
	public void setCompleted(int completed) {
		this.completed = completed;
	}

}
