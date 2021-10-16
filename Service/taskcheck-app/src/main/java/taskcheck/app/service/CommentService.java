/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.service;

import java.util.List;

import taskcheck.data.entity.Comment;

/**
 * The Interface CommentService.
 */
public interface CommentService {

	
	/**
	 * Creates the.
	 *
	 * @param comment the comment
	 * @return the comment
	 */
	Comment create(Comment comment);

	
	/**
	 * Gets the all.
	 *
	 * @param identifier the identifier
	 * @return the all
	 */
	List<Comment> getAll(String identifier);
}
